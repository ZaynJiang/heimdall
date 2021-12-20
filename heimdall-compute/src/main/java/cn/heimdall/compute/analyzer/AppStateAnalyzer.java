package cn.heimdall.compute.analyzer;

import cn.heimdall.compute.analyzer.compute.ComputeManager;
import cn.heimdall.compute.analyzer.compute.MetricComputeTask;
import cn.heimdall.core.config.constants.MessageConstants;
import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.task.MessageTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppStateAnalyzer extends AbstractMessageAnalyzer{

    //TODO 初始化

    @Override
    public void init() {
        //FIXME 优化
        analyzerTasks = new HashMap<>(1);
        List<MessageTask> tasks = new ArrayList<>(MessageConstants.MESSAGE_TREE_ANALYZER_LIST_SIZE);
        analyzerTasks.put("heartComputes", tasks);
        for (int i = 0; i < MessageConstants.MESSAGE_TREE_ANALYZER_LIST_SIZE; i++) {
            tasks.add(new MetricComputeTask(MessageConstants.MESSAGE_ANALYZER_QUEUE_SIZE,
                    ComputeManager.singleHeartbeatMetricCompute()));
        }
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.TYPE_CLIENT_APP_STATE_REQUEST;
    }

    @Override
    public void flush() {

    }
}
