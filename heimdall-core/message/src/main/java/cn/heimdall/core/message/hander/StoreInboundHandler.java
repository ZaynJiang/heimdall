package cn.heimdall.core.message.hander;

import cn.heimdall.core.message.body.store.StoreAppStateRequest;
import cn.heimdall.core.message.body.store.StoreAppStateResponse;
import cn.heimdall.core.message.body.store.StoreMetricRequest;
import cn.heimdall.core.message.body.store.StoreMetricResponse;
import cn.heimdall.core.message.body.store.StoreTraceRequest;
import cn.heimdall.core.message.body.store.StoreTraceResponse;

public interface StoreInboundHandler {
    StoreMetricResponse handle(StoreMetricRequest request) throws Exception;
    StoreTraceResponse handle(StoreTraceRequest request);
    StoreAppStateResponse handle(StoreAppStateRequest request);
}
