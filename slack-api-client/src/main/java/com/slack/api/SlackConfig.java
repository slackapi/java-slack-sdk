package com.slack.api;

import com.slack.api.audit.AuditClient;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.MethodsConfig;
import com.slack.api.scim.SCIMClient;
import com.slack.api.status.v1.LegacyStatusClient;
import com.slack.api.status.v2.StatusClient;
import com.slack.api.util.http.listener.DetailedLoggingListener;
import com.slack.api.util.http.listener.HttpResponseListener;
import com.slack.api.util.http.listener.ResponsePrettyPrintingListener;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * The basic configuration of this SDK. Some settings can be propagated to sub modules such as Bolt.
 */
@Data
public class SlackConfig {

    /**
     * The default instance is immutable. It's not allowed to modify the value runtime for any reasons.
     */
    public static final SlackConfig DEFAULT = new SlackConfig() {

        void throwException() {
            throw new UnsupportedOperationException("This config is immutable");
        }

        @Override
        public void setFailOnUnknownProperties(boolean failOnUnknownProperties) {
            throwException();
        }

        @Override
        public void setPrettyResponseLoggingEnabled(boolean prettyResponseLoggingEnabled) {
            throwException();
        }

        @Override
        public void setLibraryMaintainerMode(boolean libraryMaintainerMode) {
            throwException();
        }

        @Override
        public void setTokenExistenceVerificationEnabled(boolean tokenExistenceVerificationEnabled) {
            throwException();
        }

        @Override
        public void setHttpClientResponseHandlers(List<HttpResponseListener> httpClientResponseHandlers) {
            throwException();
        }

        @Override
        public void setAuditEndpointUrlPrefix(String auditEndpointUrlPrefix) {
            throwException();
        }

        @Override
        public void setMethodsEndpointUrlPrefix(String methodsEndpointUrlPrefix) {
            throwException();
        }

        @Override
        public void setScimEndpointUrlPrefix(String scimEndpointUrlPrefix) {
            throwException();
        }

        @Override
        public void setStatusEndpointUrlPrefix(String statusEndpointUrlPrefix) {
            throwException();
        }

        @Override
        public void setLegacyStatusEndpointUrlPrefix(String legacyStatusEndpointUrlPrefix) {
            throwException();
        }

        @Override
        public void setMethodsConfig(MethodsConfig methodsConfig) {
            throwException();
        }

        @Override
        public void setProxyUrl(String proxyUrl) {
            throwException();
        }

        @Override
        public void setHttpClientCallTimeoutMillis(Integer httpClientCallTimeoutMillis) {
            throwException();
        }

        @Override
        public void setHttpClientWriteTimeoutMillis(Integer httpClientWriteTimeoutMillis) {
            throwException();
        }

        @Override
        public void setHttpClientReadTimeoutMillis(Integer httpClientReadTimeoutMillis) {
            throwException();
        }
    };

    public SlackConfig() {
        getHttpClientResponseHandlers().add(new DetailedLoggingListener());
        getHttpClientResponseHandlers().add(new ResponsePrettyPrintingListener());
    }

    /**
     * The underlying HTTP client's read timeout (in milliseconds). The default is 10 seconds.
     * https://square.github.io/okhttp/4.x/okhttp/okhttp3/-ok-http-client/read-timeout-millis/
     */
    private Integer httpClientReadTimeoutMillis;

    /**
     * The underlying HTTP client's write timeout (in milliseconds). The default is 10 seconds.
     * https://square.github.io/okhttp/4.x/okhttp/okhttp3/-ok-http-client/write-timeout-millis/
     */
    private Integer httpClientWriteTimeoutMillis;

    /**
     * The underlying HTTP client's call timeout (in milliseconds).
     * By default there is no timeout for complete calls,
     * but there is for the connect, write, and read actions within a call.
     * https://square.github.io/okhttp/4.x/okhttp/okhttp3/-ok-http-client/call-timeout-millis/
     */
    private Integer httpClientCallTimeoutMillis;

    /**
     * The proxy server URL supposed to be used for all api calls.
     */
    private String proxyUrl = initProxyUrl();

    // This method runs only once for SINGLETON instance.
    // If you want to reflect dynamically updated system properties,
    // create a new instance by invoking the default constructor.
    private static String initProxyUrl() {
        String host = System.getProperty("http.proxyHost");
        if (host != null) {
            String port = System.getProperty("http.proxyPort");
            if (port != null) {
                return "http://" + host + ":" + port;
            } else {
                return "http://" + host;
            }
        }
        return null;
    }

    private boolean prettyResponseLoggingEnabled = false;

    /**
     * Don't enable this flag in production. This flag enables some validation features for development.
     */
    private boolean libraryMaintainerMode = false;

    /**
     * If you would like to detect unknown properties by throwing exceptions, set this flag as true.
     */
    private boolean failOnUnknownProperties = false;

    /**
     * Slack Web API client verifies the existence of tokens before sending HTTP requests to Slack servers.
     */
    private boolean tokenExistenceVerificationEnabled = false;

    private List<HttpResponseListener> httpClientResponseHandlers = new ArrayList<>();

    private String auditEndpointUrlPrefix = AuditClient.ENDPOINT_URL_PREFIX;

    private String methodsEndpointUrlPrefix = MethodsClient.ENDPOINT_URL_PREFIX;

    private String scimEndpointUrlPrefix = SCIMClient.ENDPOINT_URL_PREFIX;

    private String statusEndpointUrlPrefix = StatusClient.ENDPOINT_URL_PREFIX;

    private String legacyStatusEndpointUrlPrefix = LegacyStatusClient.ENDPOINT_URL_PREFIX;

    private MethodsConfig methodsConfig = MethodsConfig.DEFAULT_SINGLETON;

}