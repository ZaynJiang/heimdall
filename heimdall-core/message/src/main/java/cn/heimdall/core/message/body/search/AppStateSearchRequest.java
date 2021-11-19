package cn.heimdall.core.message.body.search;

public class AppStateSearchRequest extends SearchRequest{
    private String appName;
    private String host;

    public String getAppName() {
        return appName;
    }

    public AppStateSearchRequest setAppName(String appName) {
        this.appName = appName;
        return this;
    }
}
