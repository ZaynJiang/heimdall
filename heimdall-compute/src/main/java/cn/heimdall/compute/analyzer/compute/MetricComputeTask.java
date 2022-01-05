package cn.heimdall.compute.analyzer.compute;

import cn.heimdall.core.message.metric.MetricKey;
import cn.heimdall.compute.metric.MetricWhatPulse;
import cn.heimdall.core.utils.constants.MetricConstants;
import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.body.origin.MessageTreeRequest;
import cn.heimdall.core.message.task.DefaultMessageQueue;
import cn.heimdall.core.message.task.MessageQueue;
import cn.heimdall.core.message.task.MessageTask;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MetricComputeTask implements MessageTask {

    private MessageQueue messageQueue;

    private Compute compute;

    private int queueOverflow;

    private final Logger log = LogManager.getLogger(getClass());

    private static volatile Map<MetricKey, MetricWhatPulse> metricWhatPulseMap = new ConcurrentHashMap<>();

    public MetricComputeTask(int queueSize, Compute compute) {
        this.messageQueue = new DefaultMessageQueue(queueSize);
        this.compute = compute;
    }

    @Override
    public boolean offerQueue(MessageBody tree) {
        boolean result = messageQueue.offer(tree);
        if (!result) {
            queueOverflow++;
            if (queueOverflow % MetricConstants.ANALYZER_QUEUE_OVER_FLOW_COUNT == 0) {
                log.warn("队列的消息太多了：" + queueOverflow);
            }
        }
        return result;
    }

    @Override
    public void run() {
        for (;;){
            try {
                if (!messageQueue.isEmpty()) {
                    //TODO 优化dd
                    MessageTreeRequest tree = (MessageTreeRequest) messageQueue.poll();
                    compute.compute(tree);
                } else {
                   Thread.sleep(10L);
                }
            } catch (InterruptedException e) {
                log.error(e);
            }
        }
    }
}
