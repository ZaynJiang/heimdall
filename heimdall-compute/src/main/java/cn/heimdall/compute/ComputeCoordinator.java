package cn.heimdall.compute;

import cn.heimdall.compute.analyzer.AbstractMessageAnalyzer;
import cn.heimdall.compute.analyzer.AppStateAnalyzer;
import cn.heimdall.compute.analyzer.MessageTreeAnalyzer;
import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.MessageType;
import cn.heimdall.core.message.body.MessageRequest;
import cn.heimdall.core.message.body.client.AppStateRequest;
import cn.heimdall.core.message.body.client.AppStateResponse;
import cn.heimdall.core.message.body.client.ClientMessageRequest;
import cn.heimdall.core.message.body.client.MessageTreeRequest;
import cn.heimdall.core.message.body.client.MessageTreeResponse;
import cn.heimdall.core.message.hander.ComputeInboundHandler;
import cn.heimdall.core.message.MessageDoorway;
import cn.heimdall.core.network.remote.RemotingServer;
import cn.heimdall.core.utils.event.EventBus;
import cn.heimdall.core.utils.event.EventBusManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 处理计算请求协调器
 */
public final class ComputeCoordinator implements MessageDoorway, ComputeInboundHandler {

   private static final Logger LOGGER = LoggerFactory.getLogger(ComputeCoordinator.class);
   private EventBus eventBus = EventBusManager.get();
   private RemotingServer remotingServer;
   private AppStateAnalyzer appStateAnalyzer;
   private MessageTreeAnalyzer messageTreeAnalyzer;
   private Map<MessageType, AbstractMessageAnalyzer> analyzerMap;

   public ComputeCoordinator(RemotingServer remotingServer) {
      this.remotingServer = remotingServer;
      appStateAnalyzer = new AppStateAnalyzer();
      messageTreeAnalyzer = new MessageTreeAnalyzer();
      analyzerMap = new HashMap<>(MessageType.values().length);
   }

   public void init() {
      appStateAnalyzer.initTasks();
      messageTreeAnalyzer.initTasks();
      analyzerMap.put(MessageType.TYPE_CLIENT_APP_STATE_REQUEST, new AppStateAnalyzer());
      analyzerMap.put(MessageType.TYPE_CLIENT_MESSAGE_TREE_REQUEST, new MessageTreeAnalyzer());
   }

   @Override
   public MessageBody onRequest(MessageBody request) {
      //如果不是发往服务端的消息
      if (!(request instanceof MessageRequest)) {
         throw new IllegalArgumentException();
      }
      ClientMessageRequest clientMessage = (ClientMessageRequest) request;
      clientMessage.setInboundHandler(this);
      //执行inbound的方法
      return clientMessage.handle();
   }

   @Override
   public void onResponse(MessageBody response) {
      return;
   }

   @Override
   public AppStateResponse handle(AppStateRequest request) {
      analyzerMap.get(request.getMessageType()).distribute(request);
      AppStateResponse response = new AppStateResponse();
      return response;
   }

   @Override
   public MessageTreeResponse handle(MessageTreeRequest request) {
      analyzerMap.get(request.getMessageType()).distribute(request);
      return null;
   }
}
