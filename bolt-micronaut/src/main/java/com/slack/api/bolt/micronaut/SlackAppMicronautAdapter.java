package com.slack.api.bolt.micronaut;

import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.util.SlackRequestParser;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.server.netty.NettyHttpResponseFactory;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * The default adaptor that translates Micronaut specific interfaces into Bolt's ones.
 * This component requires singleton {@link AppConfig} instance managed by the Micronaut DI container.
 */
@Slf4j
@Singleton
public class SlackAppMicronautAdapter {

    private SlackRequestParser requestParser;

    public SlackAppMicronautAdapter(AppConfig appConfig) {
        this.requestParser = new SlackRequestParser(appConfig);
    }

    public Request<?> toSlackRequest(HttpRequest<?> req, String requestBody) {
        RequestHeaders headers = new RequestHeaders(
                req.getHeaders() != null ? req.getHeaders().asMap() : Collections.emptyMap());

        InetSocketAddress isa = req.getRemoteAddress();
        String remoteAddress = null;
        if (isa != null && isa.getAddress() != null) {
            remoteAddress = toString(isa.getAddress().getAddress());
        }
        SlackRequestParser.HttpRequest rawRequest = SlackRequestParser.HttpRequest.builder()
                .requestUri(req.getPath())
                .queryString(req.getParameters() != null ? req.getParameters().asMap() : Collections.emptyMap())
                .headers(headers)
                .requestBody(requestBody)
                .remoteAddress(remoteAddress)
                .build();
        return requestParser.parse(rawRequest);
    }

    private NettyHttpResponseFactory micronautResponseFactory = new NettyHttpResponseFactory();

    public HttpResponse<String> toMicronautResponse(Response resp) {
        HttpStatus status = HttpStatus.valueOf(resp.getStatusCode());
        MutableHttpResponse<String> response = micronautResponseFactory.status(status);
        for (Map.Entry<String, List<String>> header : resp.getHeaders().entrySet()) {
            String name = header.getKey();
            for (String value : header.getValue()) {
                response.header(name, value);
            }
        }
        response.body(resp.getBody());
        response.contentType(resp.getContentType());
        if (resp.getBody() != null) {
            response.contentLength(resp.getBody().length());
        } else {
            response.contentLength(0);
        }
        return response;
    }

    private static final String toString(byte[] rawBytes) {
        int i = 4;
        StringBuilder ipAddress = new StringBuilder();
        for (byte raw : rawBytes) {
            ipAddress.append(raw & 0xFF);
            if (--i > 0) {
                ipAddress.append(".");
            }
        }
        return ipAddress.toString();
    }

}
