package cn.heimdall.guarder;

import cn.heimdall.core.cluster.ClusterInfo;
import cn.heimdall.core.cluster.ClusterInfoManager;
import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.MessageDoorway;
import cn.heimdall.core.message.NodeRole;
import cn.heimdall.core.message.RpcMessage;
import cn.heimdall.core.message.body.GuarderMessageRequest;
import cn.heimdall.core.message.body.MessageRequest;
import cn.heimdall.core.message.body.heartbeat.ClientHeartbeatRequest;
import cn.heimdall.core.message.body.heartbeat.ClientHeartbeatResponse;
import cn.heimdall.core.message.body.heartbeat.NodeHeartbeatRequest;
import cn.heimdall.core.message.body.heartbeat.NodeHeartbeatResponse;
import cn.heimdall.core.message.body.register.ClientRegisterRequest;
import cn.heimdall.core.message.body.register.ClientRegisterResponse;
import cn.heimdall.core.message.body.register.NodeRegisterRequest;
import cn.heimdall.core.message.body.register.NodeRegisterResponse;
import cn.heimdall.core.message.hander.GuarderInboundHandler;
import cn.heimdall.core.utils.spi.Initialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class GuarderCoordinator implements MessageDoorway, GuarderInboundHandler, Initialize {
    private static final Logger LOGGER = LoggerFactory.getLogger(GuarderCoordinator.class);

    private ClusterInfoManager clusterInfoManager;

    private ClusterInfo clusterInfo;

    @Override
    public void init() {
        clusterInfoManager = ClusterInfoManager.getInstance();
        clusterInfo = clusterInfoManager.getClusterInfo();
    }

    @Override
    public MessageBody onRequest(MessageBody request) {
        GuarderMessageRequest guarderMessageRequest = (GuarderMessageRequest) request;
        guarderMessageRequest.setInboundHandler(this);
        //执行inbound的方法
        return guarderMessageRequest.handle();
    }

    @Override
    public void onResponse(MessageBody response) {
        //TODO do nothing
    }

    @Override
    public NodeRegisterResponse handle(NodeRegisterRequest request) {
        clusterInfoManager.doRegisterNodeInfo(request);
        NodeRegisterResponse nodeRegisterResponse = new NodeRegisterResponse(true);
        Map<NodeRole, Map<InetSocketAddress, Long>> addresses = this.getNodeRoleMap();
        nodeRegisterResponse.setAddresses(addresses);
        return nodeRegisterResponse;
    }

    @Override
    public NodeHeartbeatResponse handle(NodeHeartbeatRequest request) {
        clusterInfoManager.doUpdateNodeInfo(request);
        NodeHeartbeatResponse nodeHeartbeatResponse = new NodeHeartbeatResponse();
        Map<NodeRole, Map<InetSocketAddress, Long>> addresses = this.getNodeRoleMap();
        nodeHeartbeatResponse.setAddresses(addresses);
        return nodeHeartbeatResponse;
    }


    @Override
    public ClientRegisterResponse handle(ClientRegisterRequest request) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("guarder received client register request, appName is {}, ip is {}", request.getAppName(), request.getIp());
        }
        ClientRegisterResponse nodeHeartbeatResponse = new ClientRegisterResponse();
        Map<NodeRole, Map<InetSocketAddress, Long>> addresses = this.getNodeRoleMap();
        nodeHeartbeatResponse.setAddresses(addresses);
        return nodeHeartbeatResponse;
    }

    @Override
    public ClientHeartbeatResponse handle(ClientHeartbeatRequest request) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("guarder received client heartbeat request, appName is {}, ip is {}", request.getAppName(), request.getIp());
        }
        ClientHeartbeatResponse nodeHeartbeatResponse = new ClientHeartbeatResponse();
        Map<NodeRole, Map<InetSocketAddress, Long>> addresses = this.getNodeRoleMap();
        nodeHeartbeatResponse.setAddresses(addresses);
        return nodeHeartbeatResponse;
    }

    private Map<NodeRole, Map<InetSocketAddress, Long>> getNodeRoleMap() {
        Map<NodeRole, Map<InetSocketAddress, Long>> addresses = new HashMap<>();
        addresses.put(NodeRole.COMPUTE, clusterInfo.getComputeNodes());
        addresses.put(NodeRole.GUARDER, clusterInfo.getGuarderNodes());
        addresses.put(NodeRole.STORAGE, clusterInfo.getStorageNodes());
        return addresses;
    }


}
