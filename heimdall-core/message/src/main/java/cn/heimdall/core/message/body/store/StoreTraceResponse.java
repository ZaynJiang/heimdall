package cn.heimdall.core.message.body.store;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.MessageType;

/**
 * 存储tracelog返回body
 */
public class StoreTraceResponse extends MessageBody {
    @Override
    public MessageType getMessageType() {
        return MessageType.STORE_TRANCE_LOG_RESPONSE;
    }
}
