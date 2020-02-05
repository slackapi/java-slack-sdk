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

@Data
public class SlackConfig {

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