package cn.heimdall.core.config;

public class ConfigurationChangeEvent {
    private String configId;
    private String oldValue;
    private String newValue;
    private ConfigurationChangeType changeType;

    public ConfigurationChangeEvent(){
    }

    public ConfigurationChangeEvent(String configId, String newValue) {
        this(configId, null, newValue, ConfigurationChangeType.MODIFY);
    }

    public ConfigurationChangeEvent(String configId, String oldValue, String newValue,
                                    ConfigurationChangeType type) {
        this.configId = configId;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.changeType = type;
    }

    public String getConfigId() {
        return configId;
    }

    public ConfigurationChangeEvent setConfigId(String configId) {
        this.configId = configId;
        return this;
    }

    public String getOldValue() {
        return oldValue;
    }

    public ConfigurationChangeEvent setOldValue(String oldValue) {
        this.oldValue = oldValue;
        return this;
    }

    public String getNewValue() {
        return newValue;
    }

    public ConfigurationChangeEvent setNewValue(String newValue) {
        this.newValue = newValue;
        return this;
    }

    public ConfigurationChangeType getChangeType() {
        return changeType;
    }

    public ConfigurationChangeEvent setChangeType(ConfigurationChangeType changeType) {
        this.changeType = changeType;
        return this;
    }
}
