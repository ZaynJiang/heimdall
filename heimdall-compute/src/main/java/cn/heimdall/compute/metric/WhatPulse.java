package cn.heimdall.compute.metric;

public interface WhatPulse {
    //获取成功数量
    long getCount();
    //获取异常数量
    long getExceptionCount();
    //添加成功数量
    void addCount(int n);
    //添加异常数量
    void addException(int n);
    //获取平均响应时间
    long getTotalRT();
    //获取最小响应时间
    long getMinRT();
    //新增响应时间
    void addRT(long n);

}
