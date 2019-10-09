package com.github.seratch.jslack.lightning.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class QueryStringParser {
    private QueryStringParser() {
    }

    public static Map<String, String> toMap(String query) {
        Map<String, String> query_pairs = new LinkedHashMap<>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            try {
                String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
                String value = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
                query_pairs.put(key, value);
            } catch (UnsupportedEncodingException e) {
                query_pairs.put(pair.substring(0, idx), pair.substring(idx + 1));
            }
        }
        return query_pairs;
    }

}
