package com.slack.api.lightning.request;

import lombok.ToString;

import java.util.*;

@ToString
public class RequestHeaders {

    private final Map<String, List<String>> underlying = new HashMap<>();

    public Set<String> getNames() {
        return underlying.keySet();
    }

    public RequestHeaders(Map<String, List<String>> headers) {
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            this.underlying.put(normalizeKey(entry.getKey()), entry.getValue());
        }
    }

    public String getFirstValue(String name) {
        List<String> values = this.underlying.get(normalizeKey(name));
        if (values != null && values.size() > 0) {
            return values.get(0);
        } else {
            return null;
        }
    }

    public List<String> getMultipleValues(String name) {
        return this.underlying.get(name.toLowerCase(Locale.ENGLISH));
    }

    private static String normalizeKey(String name) {
        return name != null ? name.toLowerCase(Locale.ENGLISH) : null;
    }

}
