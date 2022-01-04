package cn.heimdall.core.message.hander;

import cn.heimdall.core.message.body.heartbeat.ClientHeartbeatRequest;
import cn.heimdall.core.message.body.heartbeat.ClientHeartbeatResponse;
import cn.heimdall.core.message.body.heartbeat.NodeHeartbeatRequest;
import cn.heimdall.core.message.body.heartbeat.NodeHeartbeatResponse;
import cn.heimdall.core.message.body.register.AppRegisterRequest;
import cn.heimdall.core.message.body.register.AppRegisterResponse;
import cn.heimdall.core.message.body.register.NodeRegisterRequest;
import cn.heimdall.core.message.body.register.NodeRegisterResponse;

public interface GuarderInboundHandler {
    /**
     * 集群节点注册
     * @param request
     * @return
     */
    NodeRegisterResponse handle(NodeRegisterRequest request);

    /**
     * 集群节点心跳
     * @param request
     * @return
     */
    NodeHeartbeatResponse handle(NodeHeartbeatRequest request);

    /**
     * 客户端注册
     * @param request
     * @return
     */
    AppRegisterResponse handle(AppRegisterRequest request);

    /**
     * 客户端心跳数据
     * @param request
     * @return
     */
    ClientHeartbeatResponse handle(ClientHeartbeatRequest request);
}
