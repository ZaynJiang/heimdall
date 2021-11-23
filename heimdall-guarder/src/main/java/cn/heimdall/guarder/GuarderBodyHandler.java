package cn.heimdall.guarder;

import cn.heimdall.core.message.Message;
import cn.heimdall.core.message.MessageDoorway;

public class GuarderBodyHandler implements MessageDoorway {

    @Override
    public Message syncHand(Message message) {
        return null;
    }
}
