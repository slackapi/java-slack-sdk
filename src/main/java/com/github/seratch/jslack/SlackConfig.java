package com.github.seratch.jslack;

import lombok.Data;

@Data
public class SlackConfig {

    public static final SlackConfig DEFAULT = new SlackConfig() {
        @Override
        public void setPrettyResponseLoggingEnabled(boolean prettyResponseLoggingEnabled) {
            throw new UnsupportedOperationException("This config is immutable");
        }

        @Override
        public void setLibraryMaintainerMode(boolean libraryMaintainerMode) {
            throw new UnsupportedOperationException("This config is immutable");
        }
    };

    private boolean prettyResponseLoggingEnabled = false;

    /**
     * Don't enable this flag in production. This flag enables some validation features for development.
     */
    private boolean libraryMaintainerMode = false;

}
