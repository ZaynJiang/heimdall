package cn.heimdall.compute.analyzer;

import cn.heimdall.compute.analyzer.compute.EventLogCompute;
import cn.heimdall.compute.analyzer.compute.SpanLogCompute;
import cn.heimdall.compute.analyzer.task.MetricComputeTask;
import cn.heimdall.compute.analyzer.task.TraceLogDumperTask;
import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.task.MessageTask;
import cn.heimdall.core.utils.annotation.LoadLevel;
import cn.heimdall.core.utils.constants.LoadLevelConstants;
import cn.heimdall.core.utils.constants.MetricConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 消息树消息分析器
 */
@LoadLevel(name = LoadLevelConstants.ANALYZER_APP_MESSAGE_TREE)
public class MessageTreeAnalyzer extends AbstractMessageAnalyzer{

    public MessageTreeAnalyzer() {
        super();
    }

    @Override
    public void flush() {

    }

    @Override
    public void init() {
        //FIXME 优化
        analyzerTasks = new HashMap<>(3);
        List<MessageTask> spanComputes = new ArrayList<>(MetricConstants.MESSAGE_TREE_ANALYZER_LIST_SIZE);
        List<MessageTask> eventComputes = new ArrayList<>(MetricConstants.MESSAGE_TREE_ANALYZER_LIST_SIZE);
        List<MessageTask> traceLogDumper = new ArrayList<>(MetricConstants.MESSAGE_TREE_ANALYZER_LIST_SIZE);
        analyzerTasks.put("spanComputes", spanComputes);
        analyzerTasks.put("eventComputes", eventComputes);
        analyzerTasks.put("traceLogDumper", traceLogDumper);
        for (int i = 0; i < MetricConstants.MESSAGE_TREE_ANALYZER_LIST_SIZE; i++) {
            spanComputes.add(new MetricComputeTask(MetricConstants.MESSAGE_ANALYZER_QUEUE_SIZE,  new SpanLogCompute()));
            eventComputes.add(new MetricComputeTask(MetricConstants.MESSAGE_ANALYZER_QUEUE_SIZE, new EventLogCompute()));
            traceLogDumper.add(new TraceLogDumperTask(MetricConstants.MESSAGE_ANALYZER_QUEUE_SIZE));
        }
        super.init();
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.MESSAGE_TREE_REQUEST;
    }
}
