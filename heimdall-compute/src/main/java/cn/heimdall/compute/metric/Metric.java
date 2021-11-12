package cn.heimdall.compute.metric;

import cn.heimdall.compute.window.WindowBucket;

public interface Metric {
    //获取成功数量
    long getCount();
    //获取异常数量
    long getExceptionCount();
    //添加成功数量
    void addCount(int n);
    //添加异常数量
    void addException(int n);
    //获取时间窗口
    WindowBucket[] windows();
}
