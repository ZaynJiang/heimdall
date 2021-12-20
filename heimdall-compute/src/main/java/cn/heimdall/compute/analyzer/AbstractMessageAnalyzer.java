package cn.heimdall.compute.analyzer;

import cn.heimdall.core.config.constants.MessageConstants;
import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.MessageTypeAware;
import cn.heimdall.core.message.body.origin.ClientMessageRequest;
import cn.heimdall.core.message.task.MessageTask;
import cn.heimdall.core.utils.spi.Initialize;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public abstract class AbstractMessageAnalyzer implements MessageTypeAware, Initialize {
    protected final ExecutorService taskExecutor = Executors.newFixedThreadPool(MessageConstants.MESSAGE_ANALYZER_THREAD_COUNT);
    //多个分析器（span、event、tree、appState等）
    protected Map<String, List<MessageTask>> analyzerTasks;

    public AbstractMessageAnalyzer() {
        analyzerTasks.forEach((name, messageTasks) ->
                messageTasks.stream().forEach(taskExecutor::submit));
    }

    public void distribute(MessageBody messageBody) {
        ClientMessageRequest clientMessage = (ClientMessageRequest)messageBody;
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

    public abstract void flush();
}
