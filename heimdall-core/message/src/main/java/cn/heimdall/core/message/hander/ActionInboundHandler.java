package cn.heimdall.core.message.hander;

import cn.heimdall.core.message.body.action.QueryMetricRequest;
import cn.heimdall.core.message.body.action.QueryMetricResponse;

public interface ActionInboundHandler {

    QueryMetricResponse handle(QueryMetricRequest request);
}
