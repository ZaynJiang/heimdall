package cn.heimdall.client;

import cn.heimdall.core.message.body.heartbeat.ClientHeartbeatRequest;
import cn.heimdall.core.message.RpcMessage;

import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

public class HeimdallClient {
    public static void main(String[] args) throws TimeoutException, InterruptedException {
        Stream.of(args).forEach(arg -> {
            String[] param = arg.split("=");
            System.setProperty(param[0], param[1]);
        });
        ClientInfoManager.getInstance().init();
        ManageRemotingClient manageRemotingClient = ManageRemotingClient.getInstance();
        //发送消息
        ClientHeartbeatRequest heartbeatRequest = new ClientHeartbeatRequest();
        Thread.sleep(1000 * 30);
        RpcMessage request = new RpcMessage(heartbeatRequest);
        Object res = manageRemotingClient.sendSyncRequest(request);
        //打印结果
        System.out.println(res.toString());
    }
}
