package cn.heimdall.core.network.remote.hook;

import cn.heimdall.core.message.Message;

public interface RemoteHook {

    void doBeforeRequest(String remoteAddr, Message request);

    void doAfterResponse(String remoteAddr, Message request, Object response);
}
