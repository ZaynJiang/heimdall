package cn.heimdall.core.message.compute;

import cn.heimdall.core.message.compute.impl.EventLogCompute;
import cn.heimdall.core.message.compute.impl.SpanLogCompute;
import cn.heimdall.core.config.constants.MessageConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageTreeAnalyzer extends AbstractMessageAnalyzer{

    public MessageTreeAnalyzer() {
        super();
    }

    @Override
    public void initTasks() {
        //FIXME 优化
        analyzerTasks = new HashMap<>(3);
        List<MessageTask> spanComputes = new ArrayList<>(MessageConstants.MESSAGE_TREE_ANALYZER_LIST_SIZE);
        List<MessageTask> eventComputes = new ArrayList<>(MessageConstants.MESSAGE_TREE_ANALYZER_LIST_SIZE);
        analyzerTasks.put("spanComputes", spanComputes);
        analyzerTasks.put("eventComputes", eventComputes);
        for (int i = 0; i < MessageConstants.MESSAGE_TREE_ANALYZER_LIST_SIZE; i++) {
            spanComputes.add(new DefaultMessageTask(new DefaultMessageQueue(MessageConstants.MESSAGE_ANALYZER_QUEUE_SIZE), new SpanLogCompute()));
            eventComputes.add(new DefaultMessageTask(new DefaultMessageQueue(MessageConstants.MESSAGE_ANALYZER_QUEUE_SIZE), new EventLogCompute()));
        }
    }
}
