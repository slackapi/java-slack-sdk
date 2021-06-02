package com.slack.api.util.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import okhttp3.Credentials;

import java.util.Map;

public class ProxyUrlUtil {

    private ProxyUrlUtil() {
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ProxyUrl {
        private String schema;
        private String username;
        private String password;
        private String host;
        private Integer port;

        public String toUrlWithoutUserAndPassword() {
            return schema + host + (port != null ? ":" + port : "");
        }
    }

    public static ProxyUrl parse(String proxyUrl) {
        if (proxyUrl == null) {
            return null;
        }
        String[] proxyUrlElements = proxyUrl.split("://");
        String schema = proxyUrlElements[0] + "://";
        String urlWithoutSchema = proxyUrlElements[1];
        String[] urlWithUserAndPasswordIfTwoElements = urlWithoutSchema.split("@");
        if (urlWithUserAndPasswordIfTwoElements.length == 2) {
            String[] userAndPassword = urlWithUserAndPasswordIfTwoElements[0].split(":");
            if (userAndPassword.length != 2) {
                throw new IllegalArgumentException("Invalid proxy URL: " + proxyUrl);
            }
            String[] hostAndPort = urlWithUserAndPasswordIfTwoElements[1].split(":");
            return ProxyUrl.builder()
                    .schema(schema)
                    .username(userAndPassword[0])
                    .password(userAndPassword[1])
                    .host(hostAndPort[0])
                    .port(hostAndPort.length == 2 ? Integer.parseInt(hostAndPort[1]) : 0)
                    .build();
        } else {
            String[] hostAndPort = proxyUrl.split("://")[1].split(":");
            return ProxyUrl.builder()
                    .schema(schema)
                    .host(hostAndPort[0])
                    .port(hostAndPort.length == 2 ? Integer.parseInt(hostAndPort[1]) : 0)
                    .build();
        }
    }

    public static void setProxyAuthorizationHeader(Map<String, String> proxyHeaders, ProxyUrl parsedProxy) {
        if (parsedProxy.getUsername() != null && parsedProxy.getPassword() != null) {
            String headerValue = Credentials.basic(parsedProxy.getUsername(), parsedProxy.getPassword());
            proxyHeaders.put("Proxy-Authorization", headerValue);
        }
    }
}
