package com.slack.api.lightning.servlet;

import com.slack.api.lightning.response.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Common utilities for Servlet compatibilities.
 */
public class ServletAdapterOps {
    private ServletAdapterOps() {
    }

    public static String doReadRequestBodyAsString(HttpServletRequest req) throws IOException {
        return req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }

    public static Map<String, List<String>> toHeaderMap(HttpServletRequest req) {
        Map<String, List<String>> headers = new HashMap<>();
        Enumeration<String> names = req.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            List<String> values = Collections.list(req.getHeaders(name));
            headers.put(name, values);
        }
        return headers;
    }

    public static void writeResponse(HttpServletResponse resp, Response slackResp) throws IOException {
        resp.setStatus(slackResp.getStatusCode());
        for (Map.Entry<String, List<String>> header : slackResp.getHeaders().entrySet()) {
            String name = header.getKey();
            for (String value : header.getValue()) {
                resp.addHeader(name, value);
            }
        }
        resp.setHeader("Content-Type", slackResp.getContentType());
        if (slackResp.getBody() != null) {
            resp.getWriter().write(slackResp.getBody());
        }
    }

}
