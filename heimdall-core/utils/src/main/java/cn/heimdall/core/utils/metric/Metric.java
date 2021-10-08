package cn.heimdall.core.utils.metric;

public interface Metric {
    //获取成功数量
    long getSuccessCount();
    //获取异常数量
    long getExceptionCount();
    //添加成功数量
    void addSuccessCount(int n);
    //添加异常数量
    void addException(int n);
}
