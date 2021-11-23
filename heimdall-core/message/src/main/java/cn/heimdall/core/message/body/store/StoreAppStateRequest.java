package cn.heimdall.core.message.body.store;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.body.MessageResponse;
import cn.heimdall.core.message.MessageDoorway;

/**
 * 存储心跳消息
 */
public class StoreAppStateRequest extends AbstractStoreRequest implements MessageDoorway {
    @Override
    public MessageType getMessageType() {
        return null;
    }


    @Override
    public MessageResponse handle() {
        return null;
    }

    @Override
    public MessageBody onRequest(MessageBody request) {
        return null;
    }

    @Override
    public void onResponse(MessageBody response) {

    }
}
