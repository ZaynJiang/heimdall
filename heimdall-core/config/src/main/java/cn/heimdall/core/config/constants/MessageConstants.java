package cn.heimdall.core.config.constants;

public interface MessageConstants {
    //分析队列大小
    int ANALYZER_QUEUE_OVER_FLOW_COUNT = 1000;
    //指标计算:跨度窗口区间
    int METRIC_SPAN_WINDOW_INTERVAL = 1000;
    //指标计算:跨度窗口个数
    int METRIC_SPAN_WINDOW_COUNT = 2;
    //指标计算:event窗口区间
    int METRIC_EVENT_WINDOW_INTERVAL = 1000;
    //指标计算:event窗口区间
    int METRIC_EVENT_WINDOW_COUNT = 2;

    /**
     * 统计最大耗时
     */
    int DEFAULT_STATISTIC_MAX_RT = 5000;


    // 消息分析器线程数
    int MESSAGE_ANALYZER_THREAD_COUNT = 20;
    int MESSAGE_ANALYZER_QUEUE_SIZE = 3000;
    int MESSAGE_TREE_ANALYZER_LIST_SIZE = 10;
}
