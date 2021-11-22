package cn.heimdall.storage.core;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.hander.MessageTransaction;

public class StorageBodyHandler implements MessageTransaction {


    @Override
    public MessageBody onRequest(MessageBody request) {
        return null;
    }

    @Override
    public void onResponse(MessageBody response) {

    }
}
