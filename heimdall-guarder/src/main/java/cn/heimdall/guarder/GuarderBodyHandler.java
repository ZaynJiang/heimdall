package cn.heimdall.guarder;

import cn.heimdall.core.message.Message;
import cn.heimdall.core.message.hander.MessageTransaction;

public class GuarderBodyHandler implements MessageTransaction {

    @Override
    public Message syncHand(Message message) {
        return null;
    }
}
