package cn.heimdall.core.network.handle;

import cn.heimdall.core.message.Message;

public interface MessageHandler {
    Message onRequest(Message message);
}
