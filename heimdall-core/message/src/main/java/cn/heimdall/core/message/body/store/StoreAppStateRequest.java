package cn.heimdall.core.message.body.store;

import cn.heimdall.core.message.Message;
import cn.heimdall.core.message.MessageHandler;
import cn.heimdall.core.message.MessageType;

/**
 * 存储心跳消息
 */
public class StoreAppStateRequest extends AbstractStoreRequest implements MessageHandler {
    @Override
    public MessageType getMessageType() {
        return null;
    }

    @Override
    public Message handle(Message message) {
        StoreAppStateRequest storeAppStateRequest = (StoreAppStateRequest)message.getMessageBody();
        return null;
    }
}
