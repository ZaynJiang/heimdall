package cn.heimdall.core.message;


import cn.heimdall.core.message.body.HeartbeatBody;
import cn.heimdall.core.message.body.MessageTreeBody;
import cn.heimdall.core.message.processor.HeartbeatProcessor;
import cn.heimdall.core.message.processor.MessageTreeProcessor;
import cn.heimdall.core.message.response.ResponseHeartbeat;
import cn.heimdall.core.message.response.ResponseMessageTree;

import java.util.function.Predicate;

public enum MessageType {
    //客户端心跳
    TYPE_CLIENT_HEART_BEAT(1, HeartbeatBody.class, ResponseHeartbeat.class,  HeartbeatProcessor.class),
    //客户端消息树
    TYPE_CLIENT_MONITOR_MESSAGE(2, MessageTreeBody.class, ResponseMessageTree.class, MessageTreeProcessor.class),
    //客戶端直接形成的报表消息
    TYPE_CLIENT_MONITOR_REPORT(3, null, null, null),
    //节点发送的监控数据持久化消息
    TYPE_NODE_MONITOR_STORAGE(4,null, null, null),
    //计算节点发送心跳的消息
    TYPE_NODE_COMPUTE_HEARTBEAT(5, null, null, null),
    //存储节点发送心跳的消息
    TYPE_NODE_STORAGE_HEARTBEAT(6,null, null, null),
    //查询节点发送心跳的消息
    TYPE_NODE_QUERY_HEARTBEAT(7,null, null, null);

    private short typeCode;
    private Class requestBody;
    private Class responseBody;
    private Class processClass;

    MessageType(int typeCode, Class requestBody, Class responseBody, Class processClass) {
        this.typeCode = (short) typeCode;
        this.requestBody = requestBody;
        this.responseBody = responseBody;
        this.processClass = processClass;
    }


    private static MessageType getMessageType(Predicate<MessageType> predicate) {
        MessageType[] values = values();
        for (MessageType messageType : values) {
            if (predicate.test(messageType)) {
                return messageType;
            }
        }
        throw new AssertionError("no found message type");
    }

    public static MessageType fromTypeCode(int type) {
        return getMessageType(requestType -> requestType.getTypeCode() == type);
    }

    public short getTypeCode() {
        return typeCode;
    }

    public Class getRequestBody() {
        return getRequestBody();
    }

    public Class getResponseBody() {
        return getResponseBody();
    }
}
