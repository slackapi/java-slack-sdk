package com.slack.api.model.event;

import lombok.Data;

import java.util.List;

/**
 * The inner event describes an array of scopes you requested but were denied access to.
 * It includes a trigger_id you may use with dialog.open or Permissions API requests.
 * <p>
 * https://docs.slack.dev/changelog/2021-01-workspace-apps-retiring-the-platform-graveyard-in-aug-2021
 */
@Data
public class ScopeDeniedEvent implements Event {

    public static final String TYPE_NAME = "scope_denied";

    private final String type = TYPE_NAME;
    private List<String> scopes;
    private String triggerId;

}