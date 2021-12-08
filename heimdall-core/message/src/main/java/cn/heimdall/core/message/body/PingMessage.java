package cn.heimdall.core.message.body;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.MessageType;

public class PingMessage extends MessageBody {

    private boolean ping = true;

    public static final PingMessage PING = new PingMessage(true);

    public static final PingMessage PONG = new PingMessage(false);

    private PingMessage(boolean ping) {
        this.ping = ping;
    }

    public boolean isPing() {
        return ping;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.TYPE_PING_MESSAGE;
    }

    @Override
    public String toString() {
        return "PingMessage{" +
                "ping=" + ping +
                '}';
    }
}
