package cn.heimdall.core.message.compute;

import cn.heimdall.core.message.body.MessageBody;
import cn.heimdall.core.message.body.MessageTreeBody;
import cn.heimdall.core.message.compute.impl.Compute;
import cn.heimdall.core.message.constants.MessageConstants;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DefaultMessageTask implements MessageTask{

    private MessageQueue messageQueue;

    private Compute compute;

    private int queueOverflow;

    private final Logger log = LogManager.getLogger(getClass());


    public DefaultMessageTask(MessageQueue messageQueue, Compute compute) {
        this.messageQueue = messageQueue;
        this.compute = compute;
    }

    @Override
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
                    //TODO 优化dd
                    MessageTreeBody tree = (MessageTreeBody) messageQueue.poll();
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
