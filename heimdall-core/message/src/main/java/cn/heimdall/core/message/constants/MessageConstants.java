package cn.heimdall.core.message.constants;

public interface MessageConstants {
    //分析队列大小
    int ANALYZER_QUEUE_OVER_FLOW_COUNT = 1000;
    //指标计算:跨度窗口区间
    int METRIC_SPAN_WINDOW_INTERVAL = 1000;
    //指标计算:跨度窗口个数
    int METRIC_SPAN_WINDOW_COUNT = 2;

    /**
     * 统计最大耗时
     */
    int DEFAULT_STATISTIC_MAX_RT = 5000;
}
