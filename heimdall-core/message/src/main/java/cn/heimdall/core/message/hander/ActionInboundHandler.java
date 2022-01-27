package cn.heimdall.core.message.hander;

import cn.heimdall.core.message.body.action.QueryAppStateRequest;
import cn.heimdall.core.message.body.action.QueryAppStateResponse;
import cn.heimdall.core.message.body.action.QueryMetricRequest;
import cn.heimdall.core.message.body.action.QueryMetricResponse;
import cn.heimdall.core.message.body.action.QueryTraceRequest;
import cn.heimdall.core.message.body.action.QueryTraceResponse;

public interface ActionInboundHandler {
    QueryMetricResponse handle(QueryMetricRequest request);
    QueryTraceResponse handle(QueryTraceRequest request);
    QueryAppStateResponse handle(QueryAppStateRequest request);
}
