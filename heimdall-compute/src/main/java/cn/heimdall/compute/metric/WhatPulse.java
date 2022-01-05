package cn.heimdall.compute.metric;

import cn.heimdall.core.message.metric.MetricNode;

import java.util.Map;

public interface WhatPulse {
    //获取成功数量
    long getSuccess();
    //获取异常数量
    long getExceptionCount();
    //添加成功数量
    void addSuccess(int n);
    //添加异常数量
    void addException(int n);
    //获取平均响应时间
    double getAvgRT();
    //获取最小响应时间
    long getMinRT();
    //新增响应时间
    void addRT(long n);
    void reset();
    Map<Long, MetricNode> metrics();
}
