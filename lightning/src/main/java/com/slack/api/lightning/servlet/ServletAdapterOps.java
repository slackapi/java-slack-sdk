package com.slack.api.lightning.servlet;

import com.slack.api.lightning.response.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ServletAdapterOps {
    private ServletAdapterOps() {
    }

    public static String doReadRequestBodyAsString(HttpServletRequest req) throws IOException {
        return req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }

    public static Map<String, String> toHeaderMap(HttpServletRequest req) {
        Map<String, String> map = new HashMap<>();
        Enumeration<String> names = req.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            map.put(name, req.getHeader(name));
        }
        return map;
    }

    public static void writeResponse(HttpServletResponse resp, Response slackResp) throws IOException {
        resp.setStatus(slackResp.getStatusCode());
        for (Map.Entry<String, String> header : slackResp.getHeaders().entrySet()) {
            resp.setHeader(header.getKey(), header.getValue());
        }
        resp.setHeader("Content-Type", slackResp.getContentType());
        if (slackResp.getBody() != null) {
            resp.getWriter().write(slackResp.getBody());
        }
    }

}
