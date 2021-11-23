package cn.heimdall.core.message;


import java.util.function.Predicate;

public enum MessageType {
    //客户端发送应用状态信息
    TYPE_CLIENT_APP_STATE_REQUEST(1, null, null, null),
    TYPE_CLIENT_APP_STATE_RESPONSE(2, null, null, null),
    //客户端发送客户端消息数
    TYPE_CLIENT_MESSAGE_TREE_REQUEST(3, null, null, null),
    TYPE_CLIENT_MESSAGE_TREE_RESPONSE(4, null, null, null),

    //客户端心跳数据（发给guarder的）
    TYPE_CLIENT_HEARTBEAT(3, null, null, null),
    //节点注册消息
    TYPE_NODE_REGISTER(4, null, null, null),
    //节点心跳消息
    TYPE_NODE_HEARTBEAT(5, null, null, null),
/*    //计算节点注册
    TYPE_COMPUTE_REGISTER(7,null, null, null),
    //计算节点心跳
    TYPE_COMPUTE_HEARTBEAT(8,null, null, null),*/
    //计算节点发送存储traceLog
    TYPE_COMPUTE_STORE_TRANCE_LOG(9,null, null, null),
    //计算节点发送存储metricLog
    TYPE_COMPUTE_STORE_METRIC(10,null, null, null),
    //计算节点发送存储应用状态信息
    TYPE_COMPUTE_STORE_APP_STATE(11,null, null, null);

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
