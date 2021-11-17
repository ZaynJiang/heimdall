package cn.heimdall.core.message.handler;

import cn.heimdall.core.message.Message;

public interface MessageHandler {
    Message onRequest(Message message);
}
