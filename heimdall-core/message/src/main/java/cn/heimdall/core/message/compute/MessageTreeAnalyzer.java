package cn.heimdall.core.message.compute;

import cn.heimdall.core.message.body.MessageBody;
import cn.heimdall.core.message.constants.MessageConstants;

import java.util.ArrayList;
import java.util.List;

public class MessageTreeAnalyzer extends AbstractMessageAnalyzer{
    private List<MessageTask> tasks;

    public MessageTreeAnalyzer() {
        super();
    }

    @Override
    public MessageTask getMessageTask(MessageBody messageBody) {
        //获取domain相关的任务
        //FIXME 这个是否可以优化程根据任务量进行负载不同的元素。
        int index = Math.abs(messageBody.getDomain().hashCode()) % tasks.size();
        return tasks.get(index);
    }

    @Override
    public void initTasks() {
        tasks = new ArrayList<>(MessageConstants.MESSAGE_TREE_ANALYZER_LIST_SIZE);
        for (int i = 0; i < MessageConstants.MESSAGE_TREE_ANALYZER_LIST_SIZE; i++) {
            tasks.add(new MessageTreeTask(new DefaultMessageQueue(MessageConstants.MESSAGE_ANALYZER_QUEUE_SIZE)));
        }
    }
}
