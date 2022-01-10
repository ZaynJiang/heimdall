package cn.heimdall.compute.analyzer.task;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.body.origin.MessageTreeRequest;
import cn.heimdall.core.message.body.store.StoreTraceRequest;
import cn.heimdall.core.message.task.DefaultMessageQueue;
import cn.heimdall.core.message.task.MessageQueue;
import cn.heimdall.core.message.task.MessageTask;
import cn.heimdall.core.message.trace.EventLog;
import cn.heimdall.core.message.trace.SpanLog;
import cn.heimdall.core.network.client.StorageRemotingClient;
import cn.heimdall.core.utils.constants.MetricConstants;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.TimeoutException;

public class TraceLogDumperTask implements MessageTask {

    private MessageQueue messageQueue;

    private int queueOverflow;

    private final Logger log = LogManager.getLogger(getClass());

    public TraceLogDumperTask(int queueSize) {
        this.messageQueue = new DefaultMessageQueue(queueSize);
    }

    @Override
    public boolean offerQueue(MessageBody tree) {
        boolean result = messageQueue.offer(tree);
        if (!result) {
            queueOverflow++;
            if (queueOverflow % MetricConstants.ANALYZER_QUEUE_OVER_FLOW_COUNT == 0) {
                log.warn("traceLog队列的消息太多了：" + queueOverflow);
            }
        }
        return result;
    }

    @Override
    public void run() {
        for (;;){
            try {
                if (!messageQueue.isEmpty()) {
                    //TODO 这里会远程发送信息。
                    MessageTreeRequest tree = (MessageTreeRequest) messageQueue.poll();
                    List<SpanLog> spanLogs = tree.getSpanLogs();
                    List<EventLog> eventLogs = tree.getEventLogs();
                    //TODO 优化,这里是否需要进行compute
                    StorageRemotingClient.getInstance().sendSyncRequest(StoreTraceRequest.getRpcMessage(spanLogs, eventLogs));
                } else {
                    Thread.sleep(10L);
                }
            } catch (InterruptedException | TimeoutException e) {
                log.error("TraceLogDumperTask messageQueue", e);
            }
        }
    }
}
