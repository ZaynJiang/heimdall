package cn.heimdall.core.message.body.store;

import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.RpcMessage;
import cn.heimdall.core.message.body.MessageResponse;
import cn.heimdall.core.message.metric.MetricNode;

public class StoreMetricRequest extends AbstractStoreRequest {

    private MetricNode metricNode;

    public StoreMetricRequest(MetricNode metricNode) {
        this.metricNode = metricNode;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.TYPE_STORE_METRIC_REQUEST;
    }

    @Override
    public MessageResponse handle() {
        return null;
    }


    public static RpcMessage getRpcMessage(MetricNode metricNode) {
        StoreMetricRequest request = new StoreMetricRequest(metricNode);
        return new RpcMessage(request);
    }
}
