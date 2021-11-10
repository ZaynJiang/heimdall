package cn.heimdall.server;

import cn.heimdall.core.config.ConfigurationFactory;

public class Starter {
    public static void main(String[] args) {
        ConfigurationFactory.getInstance().getConfig("", "");
    }
}
