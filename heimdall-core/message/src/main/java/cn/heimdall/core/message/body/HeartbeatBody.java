package cn.heimdall.core.message.body;

/**
 * 客户端的心跳等信息指标
 */
public class HeartbeatBody extends ClientMessageBody {
    private String gcJson;
    private String sysJson;
    private String threadJson;

}
