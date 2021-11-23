package cn.heimdall.core.message.hander;

import cn.heimdall.core.message.MessageBody;

/**
 * 通用的消息周期
 */
public interface OnRequest {
    //处理消息的请求
    MessageBody onRequest(MessageBody request);
    //处理消息的返回
    void onResponse(MessageBody response);
}
