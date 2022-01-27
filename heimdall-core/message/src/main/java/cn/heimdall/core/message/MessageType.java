package cn.heimdall.core.message;


import cn.heimdall.core.message.body.PingMessage;
import cn.heimdall.core.message.body.action.QueryAppStateRequest;
import cn.heimdall.core.message.body.action.QueryAppStateResponse;
import cn.heimdall.core.message.body.action.QueryMetricResponse;
import cn.heimdall.core.message.body.action.QueryTraceRequest;
import cn.heimdall.core.message.body.action.QueryTraceResponse;
import cn.heimdall.core.message.body.heartbeat.ClientHeartbeatRequest;
import cn.heimdall.core.message.body.heartbeat.ClientHeartbeatResponse;
import cn.heimdall.core.message.body.heartbeat.NodeHeartbeatRequest;
import cn.heimdall.core.message.body.heartbeat.NodeHeartbeatResponse;
import cn.heimdall.core.message.body.origin.AppStateRequest;
import cn.heimdall.core.message.body.origin.AppStateResponse;
import cn.heimdall.core.message.body.origin.ClientMessageRequest;
import cn.heimdall.core.message.body.origin.MessageTreeRequest;
import cn.heimdall.core.message.body.origin.MessageTreeResponse;
import cn.heimdall.core.message.body.register.AppRegisterRequest;
import cn.heimdall.core.message.body.register.AppRegisterResponse;
import cn.heimdall.core.message.body.register.NodeRegisterRequest;
import cn.heimdall.core.message.body.register.NodeRegisterResponse;
import cn.heimdall.core.message.body.store.StoreAppStateRequest;
import cn.heimdall.core.message.body.store.StoreAppStateResponse;
import cn.heimdall.core.message.body.store.StoreMetricRequest;
import cn.heimdall.core.message.body.store.StoreMetricResponse;
import cn.heimdall.core.message.body.store.StoreTraceRequest;
import cn.heimdall.core.message.body.store.StoreTraceResponse;

import java.util.function.Predicate;

public enum MessageType {
    //客户端发送应用状态信息
    TYPE_PING_MESSAGE(100, PingMessage.class),
    APP_STATE_REQUEST(101, AppStateRequest.class),
    APP_STATE_RESPONSE(201, AppStateResponse.class),
    //客户端发送客户端消息树
    MESSAGE_TREE_REQUEST(102, MessageTreeRequest.class),
    MESSAGE_TREE_RESPONSE(202, MessageTreeResponse.class),
    //客户端注册数据（发给guarder的）
    CLIENT_REGISTER_REQUEST(103, AppRegisterRequest.class),
    CLIENT_REGISTER_RESPONSE(203, AppRegisterResponse.class),
    //客户端心跳数据（发给guarder的）
    CLIENT_HEARTBEAT_REQUEST(104, ClientHeartbeatRequest.class),
    CLIENT_HEARTBEAT_RESPONSE(204, ClientHeartbeatResponse.class),
    //节点注册消息
    NODE_REGISTER_REQUEST(105, NodeRegisterRequest.class),
    NODE_REGISTER_RESPONSE(205, NodeRegisterResponse.class),
    //节点心跳消息
    NODE_HEARTBEAT_REQUEST(106, NodeHeartbeatRequest.class),
    NODE_HEARTBEAT_RESPONSE(206, NodeHeartbeatResponse.class),
    //计算节点发送存储traceLog
    STORE_TRANCE_LOG_REQUEST(107, StoreTraceRequest.class),
    STORE_TRANCE_LOG_RESPONSE(207, StoreTraceResponse.class),
    //计算节点发送存储metricLog
    STORE_METRIC_REQUEST(108, StoreMetricRequest.class),
    STORE_METRIC_RESPONSE(208, StoreMetricResponse.class),
    //计算节点发送存储应用状态信息
    STORE_APP_STATE_REQUEST(109, StoreAppStateRequest.class),
    STORE_APP_STATE_RESPONSE(209, StoreAppStateResponse.class),

    //查询相关接口
    TYPE_QUERY_METRIC_REQUEST(130, QueryAppStateRequest.class),
    TYPE_QUERY_METRIC_RESPONSE(231, QueryMetricResponse.class),
    TYPE_QUERY_APP_STATE_REQUEST(140, QueryAppStateRequest.class),
    TYPE_QUERY_APP_STATE_RESPONSE(241, QueryAppStateResponse.class),
    TYPE_QUERY_TRANCE_LOG_REQUEST(150, QueryTraceRequest.class),
    TYPE_QUERY_TRANCE_LOG_RESPONSE(251, QueryTraceResponse.class);

    private short typeCode;
    private Class messageBodyClass;

    MessageType(int typeCode, Class messageBodyClass) {
        this.typeCode = (short) typeCode;
        this.messageBodyClass = messageBodyClass;
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

    public static MessageType fromTypeCode(short type) {
        return getMessageType(requestType -> requestType.getTypeCode() == type);
    }

    public short getTypeCode() {
        return typeCode;
    }

    public Class getMessageBodyClass() {
        return messageBodyClass;
    }
}
