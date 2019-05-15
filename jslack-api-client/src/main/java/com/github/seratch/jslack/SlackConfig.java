package com.github.seratch.jslack;

import com.github.seratch.jslack.common.http.listener.DetailedLoggingListener;
import com.github.seratch.jslack.common.http.listener.HttpResponseListener;
import com.github.seratch.jslack.common.http.listener.ResponsePrettyPrintingListener;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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

    public SlackConfig() {
        getHttpClientResponseHandlers().add(new DetailedLoggingListener());
        getHttpClientResponseHandlers().add(new ResponsePrettyPrintingListener());
    }

    private boolean prettyResponseLoggingEnabled = false;

    /**
     * Don't enable this flag in production. This flag enables some validation features for development.
     */
    private boolean libraryMaintainerMode = false;

    private List<HttpResponseListener> httpClientResponseHandlers = new ArrayList<>();

}