package cn.heimdall.compute.analyzer;

import cn.heimdall.compute.analyzer.compute.Compute;
import cn.heimdall.compute.analyzer.compute.MetricComputeTask;
import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.task.MessageTask;
import cn.heimdall.core.utils.constants.LoadLevelConstants;
import cn.heimdall.core.utils.constants.MetricConstants;
import cn.heimdall.core.utils.spi.EnhancedServiceLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 应用状态消息分析器
 */
public class AppStateAnalyzer extends AbstractMessageAnalyzer{

    //TODO 初始化

    @Override
    public void init() {
        //FIXME 优化
        analyzerTasks = new HashMap<>(1);
        List<MessageTask> tasks = new ArrayList<>(MetricConstants.MESSAGE_TREE_ANALYZER_LIST_SIZE);
        analyzerTasks.put("appStateTask", tasks);
        for (int i = 0; i < MetricConstants.MESSAGE_TREE_ANALYZER_LIST_SIZE; i++) {
            tasks.add(new MetricComputeTask(MetricConstants.MESSAGE_ANALYZER_QUEUE_SIZE,
                    EnhancedServiceLoader.load(Compute.class, LoadLevelConstants.APP_STATE_COMPUTE)));
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
