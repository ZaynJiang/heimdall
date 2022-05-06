package cn.heimdall.client.agent.bootstrap;

import cn.heimdall.client.agent.core.conf.AgentConfigInitializer;
import cn.heimdall.client.agent.core.exception.PluginException;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.lang.instrument.Instrumentation;

public class HeimdallBootstrap {
    private static Logger LOGGER = LoggerFactory.getLogger(HeimdallBootstrap.class);

    public static void premain(String agentArgs, Instrumentation instrumentation) throws PluginException {
        AgentConfigInitializer.initializeAgentConfig(agentArgs);
    }

}
