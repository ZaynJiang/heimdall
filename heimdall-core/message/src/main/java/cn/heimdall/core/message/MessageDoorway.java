package cn.heimdall.core.message;

import cn.heimdall.core.message.MessageBody;

/**
 * 通用的消息周期
 */
public interface MessageDoorway {
    //处理消息的请求
    MessageBody onRequest(MessageBody request);
    //处理消息的返回
    void onResponse(MessageBody response);
}
