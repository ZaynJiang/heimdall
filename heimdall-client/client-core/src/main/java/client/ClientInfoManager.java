package client;

import cn.heimdall.core.config.Configuration;
import cn.heimdall.core.config.ConfigurationFactory;
import cn.heimdall.core.utils.common.NetUtil;
import cn.heimdall.core.utils.constants.ConfigurationKeys;
import cn.heimdall.core.utils.enums.NodeRole;
import cn.heimdall.core.utils.spi.Initialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ClientInfoManager implements Initialize {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientInfoManager.class);

    private static volatile ClientInfoManager INSTANCE;

    private final ClientInfo clientInfo;

    private static final Configuration CONFIG = ConfigurationFactory.getInstance();

    public ClientInfoManager() {
        clientInfo = new ClientInfo();
    }

    public static ClientInfoManager getInstance() {
        if (INSTANCE == null) {
            synchronized (ClientInfoManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ClientInfoManager();
                    INSTANCE.init();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void init() {
        clientInfo.setAppName(CONFIG.getConfigFromSys(ConfigurationKeys.CLIENT_APP_NAME))
                .setHost(NetUtil.getLocalIp());
      //  clientInfo.s
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public List<NodeRole> getNodeRoles() {
        return clientInfo.getNodeRoles();
    }
}
