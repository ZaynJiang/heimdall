
package cn.heimdall.core.network.remote;

import cn.heimdall.core.network.bootstrap.NettyClientBootstrap;
import io.netty.channel.Channel;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyKeyPoolFactory implements KeyedPooledObjectFactory<NettyPoolKey, Channel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyKeyPoolFactory.class);

    private final AbstractRemotingClient remotingClient;

    private final NettyClientBootstrap clientBootstrap;

    public NettyKeyPoolFactory(AbstractRemotingClient rpcRemotingClient, NettyClientBootstrap clientBootstrap) {
        this.remotingClient = rpcRemotingClient;
        this.clientBootstrap = clientBootstrap;
    }

    @Override
    public PooledObject<Channel> makeObject(NettyPoolKey nettyPoolKey) throws Exception {
        return null;
    }

    @Override
    public void destroyObject(NettyPoolKey nettyPoolKey, PooledObject<Channel> pooledObject) throws Exception {

    }

    @Override
    public boolean validateObject(NettyPoolKey nettyPoolKey, PooledObject<Channel> pooledObject) {
        return false;
    }

    @Override
    public void activateObject(NettyPoolKey nettyPoolKey, PooledObject<Channel> pooledObject) throws Exception {

    }

    @Override
    public void passivateObject(NettyPoolKey nettyPoolKey, PooledObject<Channel> pooledObject) throws Exception {

    }

}
