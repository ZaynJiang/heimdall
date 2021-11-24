package cn.heimdall.core.message;

/**
 * 消息出入口
 */
public interface MessageDoorway {
    //处理消息的请求
    MessageBody onRequest(MessageBody request);
    //处理消息的返回
    void onResponse(MessageBody response);
}
