package com.slack.api.lightning.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class QueryStringParser {
    private QueryStringParser() {
    }

    public static Map<String, List<String>> toMap(String query) {
        if (query == null) {
            return null;
        }
        Map<String, List<String>> queryParams = new LinkedHashMap<>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            try {
                if (pair == null || pair.trim().isEmpty()) {
                    continue;
                }
                String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
                String value = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
                List<String> values = queryParams.get(key);
                if (values == null) {
                    values = new ArrayList<>();
                }
                values.add(value);
                queryParams.put(key, values);
            } catch (UnsupportedEncodingException e) {
                String key = pair.substring(0, idx);
                String value = pair.substring(idx + 1);
                List<String> values = queryParams.get(key);
                if (values == null) {
                    values = new ArrayList<>();
                }
                values.add(value);
                queryParams.put(key, values);
            }
        }
        return queryParams;
    }

}
