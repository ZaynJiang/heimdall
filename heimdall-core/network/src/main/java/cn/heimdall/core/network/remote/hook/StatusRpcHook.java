package cn.heimdall.core.network.remote.hook;

import cn.heimdall.core.message.Message;
import cn.heimdall.core.network.remote.RemoteStatus;

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
