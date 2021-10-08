package cn.heimdall.core.utils.metric;

/**
 * 跨度指标
 */
public interface SpanMetric extends Metric {

    //获取平均响应时间
    long getAvgRT();

    //获取最小响应时间
    long getMinRT();

    //新增响应时间
    void addRT(long n);

}
