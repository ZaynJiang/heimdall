package cn.heimdall.core.message.compute;

import cn.heimdall.core.config.constants.MessageConstants;
import cn.heimdall.core.message.body.MessageBody;
import cn.heimdall.core.message.body.MessageTreeBody;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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
            if (queueOverflow % MessageConstants.ANALYZER_QUEUE_OVER_FLOW_COUNT == 0) {
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
                    //TODO 优化dd
                    MessageTreeBody tree = (MessageTreeBody) messageQueue.poll();
                } else {
                    Thread.sleep(10L);
                }
            } catch (InterruptedException e) {
                log.error(e);
            }
        }
    }
}
