package cn.heimdall.core.message.compute;

import cn.heimdall.core.config.constants.MessageConstants;
import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.body.ClientMessageBody;
import cn.heimdall.core.message.task.MessageTask;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public abstract class AbstractMessageAnalyzer {
    protected final ExecutorService taskExecutor = Executors.newFixedThreadPool(MessageConstants.MESSAGE_ANALYZER_THREAD_COUNT);
    //多个分析器（span、event、tree、heartbeat等）
    protected Map<String, List<MessageTask>> analyzerTasks;

    public AbstractMessageAnalyzer() {
        initTasks();
        analyzerTasks.forEach((name, messageTasks) ->
                messageTasks.stream().forEach(taskExecutor::submit));
    }

    public void distribute(MessageBody messageBody) {
        ClientMessageBody clientMessage = (ClientMessageBody)messageBody;
        //遍历多个分析器
        for (Map.Entry<String, List<MessageTask>> entry : analyzerTasks.entrySet()) {
            //获取domain相关的任务
            //FIXME 这个是否可以优化程根据任务量进行负载不同的元素。
            List<MessageTask> tasks = entry.getValue();
            int len = tasks.size();
            int index = Math.abs(clientMessage.getDomain().hashCode()) % len;
            MessageTask messageTask = tasks.get(index);
            messageTask.offerQueue(messageBody);
        }
    }

    public abstract void initTasks();
}
