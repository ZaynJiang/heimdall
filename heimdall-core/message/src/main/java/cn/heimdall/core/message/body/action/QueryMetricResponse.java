package cn.heimdall.core.message.body.action;

import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.body.MessageResponse;

public class QueryMetricResponse extends MessageResponse {
    @Override
    public MessageType getMessageType() {
        return MessageType.ACTION_QUERY_METRIC_RESPONSE;
    }
}
