package com.github.seratch.jslack;

public class SlackTestConfig {

    private SlackTestConfig() {
    }

    private static final SlackConfig CONFIG = new SlackConfig();

    static {
        CONFIG.setLibraryMaintainerMode(true);
        CONFIG.setPrettyResponseLoggingEnabled(true);
    }

    public static SlackConfig get() {
        return CONFIG;
    }

}
