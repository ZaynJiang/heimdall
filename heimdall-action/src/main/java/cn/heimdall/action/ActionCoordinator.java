package cn.heimdall.action;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.MessageDoorway;

public class ActionCoordinator implements MessageDoorway {
    @Override
    public MessageBody onRequest(MessageBody request) {
        return null;
    }

    @Override
    public void onResponse(MessageBody response) {

    }
}
