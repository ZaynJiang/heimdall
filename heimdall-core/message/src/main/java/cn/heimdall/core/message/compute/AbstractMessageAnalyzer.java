package cn.heimdall.core.message.compute;

import cn.heimdall.core.message.body.MessageBody;
import cn.heimdall.core.message.constants.MessageConstants;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public abstract class AbstractMessageAnalyzer {

    protected List<MessageTask> tasks;

    public AbstractMessageAnalyzer() {
        ExecutorService taskExecutor = Executors.newFixedThreadPool(MessageConstants.MESSAGE_ANALYZER_THREAD_COUNT);
        initTasks();
        tasks.forEach(task -> taskExecutor.submit(task));
    }

    public void distribute(MessageBody messageBody) {
        MessageTask messageTask = getMessageTask(messageBody);
        messageTask.offerQueue(messageBody);
    }

    public abstract MessageTask getMessageTask(MessageBody messageBody);

    public abstract void initTasks();
}
