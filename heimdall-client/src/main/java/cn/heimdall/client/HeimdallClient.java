package cn.heimdall.client;

import cn.heimdall.core.message.body.register.ClientRegisterRequest;
import cn.heimdall.core.message.request.RequestMessage;

import java.util.concurrent.TimeoutException;

public class HeimdallClient {
    public static void main(String[] args) throws TimeoutException {
        ClientInfoManager.getInstance().init();
        ManageRemotingClient manageRemotingClient = ManageRemotingClient.getInstance();
        ClientRegisterRequest registerMessage = new ClientRegisterRequest();
        RequestMessage request = new RequestMessage(registerMessage);
        Object res = manageRemotingClient.sendSyncRequest(request);
        System.out.println(res.toString());
    }
}
