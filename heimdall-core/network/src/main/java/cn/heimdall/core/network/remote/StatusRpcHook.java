package cn.heimdall.core.network.remote;

import cn.heimdall.core.message.Message;

public class StatusRpcHook implements RemoteHook {

    @Override
    public void doBeforeRequest(String remoteAddr, Message request) {
        RemoteStatus.beginCount(remoteAddr);
    }

    @Override
    public void doAfterResponse(String remoteAddr, Message request, Object response) {
        RemoteStatus.endCount(remoteAddr);
    }
}
