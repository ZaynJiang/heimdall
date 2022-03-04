package cn.heimdall.core.message.hander;

import cn.heimdall.core.message.body.store.StoreAppStateRequest;
import cn.heimdall.core.message.body.store.StoreAppStateResponse;
import cn.heimdall.core.message.body.store.StoreMetricRequest;
import cn.heimdall.core.message.body.store.StoreMetricResponse;
import cn.heimdall.core.message.body.store.StoreTraceRequest;
import cn.heimdall.core.message.body.store.StoreTraceResponse;
import cn.heimdall.core.message.body.store.search.SearchAppStateRequest;
import cn.heimdall.core.message.body.store.search.SearchAppStateResponse;
import cn.heimdall.core.message.body.store.search.SearchMetricRequest;
import cn.heimdall.core.message.body.store.search.SearchMetricResponse;
import cn.heimdall.core.message.body.store.search.SearchTraceRequest;
import cn.heimdall.core.message.body.store.search.SearchTraceResponse;

public interface StoreInboundHandler {
    //存储
    StoreMetricResponse handle(StoreMetricRequest request);
    StoreTraceResponse handle(StoreTraceRequest request);
    StoreAppStateResponse handle(StoreAppStateRequest request);
    //查询
    SearchAppStateResponse handle(SearchAppStateRequest request);
    SearchTraceResponse handle(SearchTraceRequest request);
    SearchMetricResponse handle(SearchMetricRequest request);
}
