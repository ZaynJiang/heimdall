package cn.heimdall.core.message.compute;

import cn.heimdall.core.message.body.MessageBody;
import cn.heimdall.core.message.body.MessageTreeBody;
import cn.heimdall.core.message.constants.MessageConstants;
import cn.heimdall.core.message.metric.MetricKey;
import cn.heimdall.core.message.metric.SpanMetricKey;
import cn.heimdall.core.message.trace.Span;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

public class MessageTreeTask implements Runnable{

    private MessageQueue messageQueue;

    private int queueOverflow;

    private final Logger log = LogManager.getLogger(getClass());

    private int m_index;

    private MetricManager spanMetricManager;

    public MessageTreeTask(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    public boolean offerQueue(MessageBody tree) {
        boolean result = messageQueue.offer(tree);
        if (!result) {
            queueOverflow++;
            if (queueOverflow % MessageConstants.ANALYZER_QUEUE_OVER_FLOW_COUNT == 0) {
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
                    //FIXME 优化
                    MessageTreeBody tree = (MessageTreeBody) messageQueue.poll();
                    List<Span> childSpans = tree.getSpans();
                    childSpans.stream().forEach(span -> spanMetricManager.invokeSpanMetric(new SpanMetricKey(tree, span), span));
                } else {
                   Thread.sleep(10L);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
