package cn.heimdall.core.message.body.store;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.body.ServerResponse;
import cn.heimdall.core.message.hander.MessageTransaction;

/**
 * 存储心跳消息
 */
public class StoreAppStateRequest extends AbstractStoreRequest implements MessageTransaction {
    @Override
    public MessageType getMessageType() {
        return null;
    }


    @Override
    public ServerResponse handle() {
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
