package cn.heimdall.compute.analyzer;

import cn.heimdall.compute.analyzer.task.AppStateDumperTask;
import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.task.MessageTask;
import cn.heimdall.core.utils.annotation.LoadLevel;
import cn.heimdall.core.utils.constants.LoadLevelConstants;
import cn.heimdall.core.utils.constants.MetricConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 应用状态消息分析器
 */
@LoadLevel(name = LoadLevelConstants.ANALYZER_APP_STATE_MESSAGE)
public class AppStateAnalyzer extends AbstractMessageAnalyzer{

    public AppStateAnalyzer(){
        super();
    }

    //TODO 初始化

    @Override
    public void init() {
        //FIXME 优化
        analyzerTasks = new HashMap<>(1);
        List<MessageTask> tasks = new ArrayList<>(MetricConstants.MESSAGE_TREE_ANALYZER_LIST_SIZE);
        analyzerTasks.put("appStateTask", tasks);
        for (int i = 0; i < MetricConstants.MESSAGE_TREE_ANALYZER_LIST_SIZE; i++) {
            tasks.add(new AppStateDumperTask(MetricConstants.MESSAGE_ANALYZER_QUEUE_SIZE));
        }
        super.init();
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.APP_STATE_REQUEST;
    }

    @Override
    public void flush() {

    }
}
