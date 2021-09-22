package cn.heimdall.core.message.common;

public interface MessageType {
    //客戶端心跳消息
    short TYPE_CLIENT_KEEP_LIVE = 1;
    //客户端发送的监控消息
    short TYPE_CLIENT_MONITOR_MESSAGE = 3;
    //客戶端直接形成的报表消息
    short TYPE_CLIENT_MONITOR_REPORT = 4;
    //节点发送的监控数据持久化消息
    short TYPE_NODE_MONITOR_STORAGE = 5;
    //计算节点发送心跳的消息
    short TYPE_NODE_COMPUTE_HEARTBEAT = 6;
    //存储节点发送心跳的消息
    short TYPE_NODE_STORAGE_HEARTBEAT = 7;
    //查询节点发送心跳的消息
    short TYPE_NODE_QUERY_HEARTBEAT = 8;

}
