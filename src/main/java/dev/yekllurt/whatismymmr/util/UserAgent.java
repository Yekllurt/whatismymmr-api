package dev.yekllurt.whatismymmr.util;

public class UserAgent {

    private final String platform;
    private final String appId;
    private final String version;

    public UserAgent(String platform, String appId, String version) {
        this.platform = platform;
        this.appId = appId;
        this.version = version;
    }

    @Override
    public String toString() {
        return String.format("%s:%s:%s", platform, appId, version);
    }

}
