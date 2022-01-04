package cn.heimdall.client;

import cn.heimdall.core.message.body.heartbeat.ClientHeartbeatRequest;
import cn.heimdall.core.message.RpcMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

public class HeimdallClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(HeimdallClient.class);
    public static void main(String[] args) throws TimeoutException, InterruptedException {
        Stream.of(args).forEach(arg -> {
            String[] param = arg.split("=");
            System.setProperty(param[0], param[1]);
        });
        ClientInfoManager.getInstance().init();
        GuarderRemotingClient guarderRemotingClient = GuarderRemotingClient.getInstance();
        for (int i = 0; i < 1000; i++) {
            //发送消息
            ClientHeartbeatRequest heartbeatRequest = new ClientHeartbeatRequest();
            Thread.sleep(1000 * 10);
            RpcMessage request = new RpcMessage(heartbeatRequest);
            LOGGER.info("第" + i + "条消息发送开始");
            Object res = guarderRemotingClient.sendSyncRequest(request);
            //打印结果
            LOGGER.info("第" + i + "条消息发送成功" + res.toString());
            try {
                Thread.sleep(1000L);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
