package cn.heimdall.compute.metric;

import cn.heimdall.compute.window.WindowBucket;

import java.util.List;

public interface Metric {
    //获取成功数量
    long getSuccess();

    //获取异常数量
    long getExceptionCount();

    //添加成功数量
    void addSuccess(int n);

    //添加异常数量
    void addException(int n);

    //获取时间窗口
    WindowBucket[] windows();

    long getTotalRT();

    long getMinRT();

    void addRT(long rt);

    List<MetricNode> details();

}
