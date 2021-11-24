package cn.heimdall.core.message;


import java.util.function.Predicate;

public enum MessageType {
    //客户端发送应用状态信息
    TYPE_CLIENT_APP_STATE_REQUEST(101, null, null, null),
    TYPE_CLIENT_APP_STATE_RESPONSE(201, null, null, null),
    //客户端发送客户端消息树
    TYPE_CLIENT_MESSAGE_TREE_REQUEST(102, null, null, null),
    TYPE_CLIENT_MESSAGE_TREE_RESPONSE(202, null, null, null),
    //客户端心跳数据（发给guarder的）
    TYPE_CLIENT_REGISTER_REQUEST(103, null, null, null),
    TYPE_CLIENT_REGISTER_RESPONSE(203, null, null, null),
    //客户端心跳数据（发给guarder的）
    TYPE_CLIENT_HEARTBEAT_REQUEST(104, null, null, null),
    TYPE_CLIENT_HEARTBEAT_RESPONSE(204, null, null, null),
    //节点注册消息
    TYPE_NODE_REGISTER_REQUEST(105, null, null, null),
    TYPE_NODE_REGISTER_RESPONSE(205, null, null, null),
    //节点心跳消息
    TYPE_NODE_HEARTBEAT_REQUEST(106, null, null, null),
    TYPE_NODE_HEARTBEAT_RESPONSE(206, null, null, null),
    //计算节点发送存储traceLog
    TYPE_STORE_TRANCE_LOG_REQUEST(107,null, null, null),
    TYPE_STORE_TRANCE_LOG_RESPONSE(207,null, null, null),
    //计算节点发送存储metricLog
    TYPE_STORE_METRIC_REQUEST(108,null, null, null),
    TYPE_STORE_METRIC_RESPONSE(208,null, null, null),
    //计算节点发送存储应用状态信息
    TYPE_STORE_APP_STATE_REQUEST(109,null, null, null),
    TYPE_STORE_APP_STATE_RESPONSE(209,null, null, null);

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
        return this.requestBody;
    }

    public Class getResponseBody() {
        return this.responseBody;
    }
}
