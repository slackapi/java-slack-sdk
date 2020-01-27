package com.slack.api.model.event;

import lombok.Data;

import java.util.List;

/**
 *
 */
@Data
public class ScopeGrantedEvent implements Event {

    public static final String TYPE_NAME = "scope_granted";

    private final String type = TYPE_NAME;
    private List<String> scopes;
    private String triggerId;

}