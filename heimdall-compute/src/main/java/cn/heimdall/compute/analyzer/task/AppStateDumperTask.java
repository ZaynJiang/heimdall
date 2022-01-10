package cn.heimdall.compute.analyzer.task;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.body.origin.AppStateRequest;
import cn.heimdall.core.message.body.store.StoreAppStateRequest;
import cn.heimdall.core.message.task.DefaultMessageQueue;
import cn.heimdall.core.message.task.MessageQueue;
import cn.heimdall.core.message.task.MessageTask;
import cn.heimdall.core.network.client.StorageRemotingClient;
import cn.heimdall.core.utils.constants.MetricConstants;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeoutException;

public class AppStateDumperTask implements MessageTask {

    private MessageQueue messageQueue;

    private int queueOverflow;

    private final Logger log = LogManager.getLogger(getClass());

    public AppStateDumperTask(int queueSize) {
        this.messageQueue = new DefaultMessageQueue(queueSize);
    }

    @Override
    public boolean offerQueue(MessageBody body) {
        boolean result = messageQueue.offer(body);
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
                    AppStateRequest appStateRequest = (AppStateRequest) messageQueue.poll();
                    //TODO 这里需要将appStateRequest转化为storeRequest
                    StorageRemotingClient.getInstance().sendSyncRequest(StoreAppStateRequest.getRpcMessage(appStateRequest));
                } else {
                    Thread.sleep(10L);
                }
            } catch (InterruptedException | TimeoutException e) {
                log.error("AppStateRequestTask messageQueue", e);
            }
        }
    }
}
