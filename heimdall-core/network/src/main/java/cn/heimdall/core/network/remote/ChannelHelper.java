package cn.heimdall.core.network.remote;

import cn.heimdall.core.config.HeimdallConfig;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class ChannelHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelHelper.class);

    private static final ConcurrentMap<Channel, Channel> IDENTIFIED_CHANNELS = new ConcurrentHashMap<>();

    public static String getAddressFromChannel(Channel channel) {
        SocketAddress socketAddress = channel.remoteAddress();
        String address = socketAddress.toString();
        if (socketAddress.toString().indexOf(HeimdallConfig.ENDPOINT_BEGIN_CHAR) == 0) {
            address = socketAddress.toString().substring(HeimdallConfig.ENDPOINT_BEGIN_CHAR.length());
        }
        return address;
    }

    public static Channel getSameClientChannel(Channel channel) {
        if (channel.isActive()) {
            return channel;
        }
        return null;
    }

    public static Channel getContextFromIdentified(Channel channel) {
        return IDENTIFIED_CHANNELS.get(channel);
    }

    public static String getClientIpFromChannel(Channel channel) {
        String address = getAddressFromChannel(channel);
        String clientIp = address;
        if (clientIp.contains(HeimdallConfig.IP_PORT_SPLIT_CHAR)) {
            clientIp = clientIp.substring(0, clientIp.lastIndexOf(HeimdallConfig.IP_PORT_SPLIT_CHAR));
        }
        return clientIp;
    }

    public static Integer getClientPortFromChannel(Channel channel) {
        String address = getAddressFromChannel(channel);
        Integer port = 0;
        try {
            if (address.contains(HeimdallConfig.IP_PORT_SPLIT_CHAR)) {
                port = Integer.parseInt(address.substring(address.lastIndexOf(HeimdallConfig.IP_PORT_SPLIT_CHAR) + 1));
            }
        } catch (NumberFormatException exx) {
            LOGGER.error(exx.getMessage());
        }
        return port;
    }
}
