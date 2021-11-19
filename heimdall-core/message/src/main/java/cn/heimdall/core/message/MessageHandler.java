package cn.heimdall.core.message;

import cn.heimdall.core.message.Message;

public interface MessageHandler {
    //处理
    Message handle(Message message);
}
