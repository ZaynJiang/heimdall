package cn.heimdall.compute;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.body.ServerRequest;
import cn.heimdall.core.message.body.client.AppStateRequest;
import cn.heimdall.core.message.body.client.AppStateResponse;
import cn.heimdall.core.message.body.client.ClientMessageRequest;
import cn.heimdall.core.message.body.client.MessageTreeRequest;
import cn.heimdall.core.message.body.client.MessageTreeResponse;
import cn.heimdall.core.message.hander.ComputeInboundHandler;
import cn.heimdall.core.message.hander.MessageTransaction;

public class ComputeCoordinator implements MessageTransaction, ComputeInboundHandler {

   @Override
   public MessageBody onRequest(MessageBody request) {
      //如果不是发往服务端的消息
      if (!(request instanceof ServerRequest)) {
         throw new IllegalArgumentException();
      }
      ClientMessageRequest clientMessage = (ClientMessageRequest) request;
      clientMessage.setInboundHandler(this);
      //执行inbound的方法
      return clientMessage.handle();
   }

   @Override
   public void onResponse(MessageBody response) {

   }

   @Override
   public AppStateResponse handle(AppStateRequest request) {
      return null;
   }

   @Override
   public MessageTreeResponse handle(MessageTreeRequest request) {
      return null;
   }
}
