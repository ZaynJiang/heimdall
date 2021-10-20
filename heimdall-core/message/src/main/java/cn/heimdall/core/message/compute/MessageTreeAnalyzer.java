package cn.heimdall.core.message.compute;

import cn.heimdall.core.config.constants.MessageConstants;
import cn.heimdall.core.message.task.MessageTask;
import cn.heimdall.core.message.task.MetricComputeTask;
import cn.heimdall.core.message.task.TraceLogDumperTask;

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
        List<MessageTask> traceLogDumper = new ArrayList<>(MessageConstants.MESSAGE_TREE_ANALYZER_LIST_SIZE);
        analyzerTasks.put("spanComputes", spanComputes);
        analyzerTasks.put("eventComputes", eventComputes);
        analyzerTasks.put("traceLogDumper", traceLogDumper);
        for (int i = 0; i < MessageConstants.MESSAGE_TREE_ANALYZER_LIST_SIZE; i++) {
            spanComputes.add(new MetricComputeTask(MessageConstants.MESSAGE_ANALYZER_QUEUE_SIZE, ComputeManager.singleSpanMetricCompute()));
            eventComputes.add(new MetricComputeTask(MessageConstants.MESSAGE_ANALYZER_QUEUE_SIZE, ComputeManager.singleEventMetricCompute()));
            traceLogDumper.add(new TraceLogDumperTask(MessageConstants.MESSAGE_ANALYZER_QUEUE_SIZE));
        }
    }
}
