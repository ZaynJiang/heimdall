package cn.heimdall.core.message.compute;

import cn.heimdall.core.config.constants.MessageConstants;
import cn.heimdall.core.message.task.MessageTask;
import cn.heimdall.core.message.task.MetricComputeTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HeartbeatAnalyzer extends AbstractMessageAnalyzer{

    //TODO 初始化

    @Override
    public void initTasks() {
        //FIXME 优化
        analyzerTasks = new HashMap<>(1);
        List<MessageTask> tasks = new ArrayList<>(MessageConstants.MESSAGE_TREE_ANALYZER_LIST_SIZE);
        analyzerTasks.put("heartComputes", tasks);
        for (int i = 0; i < MessageConstants.MESSAGE_TREE_ANALYZER_LIST_SIZE; i++) {
            tasks.add(new MetricComputeTask(MessageConstants.MESSAGE_ANALYZER_QUEUE_SIZE,
                    ComputeManager.singleHeartbeatMetricCompute()));
        }
    }
}
