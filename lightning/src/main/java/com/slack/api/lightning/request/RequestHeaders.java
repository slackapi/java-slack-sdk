package com.slack.api.lightning.request;

import lombok.ToString;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@ToString
public class RequestHeaders extends HashMap<String, String> {

    private final Map<String, String> underlying = new HashMap<>();

    public Set<String> getHeaderNames() {
        return underlying.keySet();
    }

    public RequestHeaders(Map<String, String> headers) {
        for (Entry<String, String> entry : headers.entrySet()) {
            this.underlying.put(entry.getKey().toLowerCase(Locale.ENGLISH), entry.getValue());
        }
    }

    public String get(String name) {
        return this.underlying.get(name.toLowerCase(Locale.ENGLISH));
    }

}
