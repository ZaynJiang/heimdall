package cn.heimdall.core.message.body;

/**
 * 客户端的jvm等指标
 */
public class HeartbeatBody extends MessageBody{
    private String gcJson;
    private String sysJson;
    private String threadJson;
}
