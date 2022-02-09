package com.slack.api.methods.response.admin.users.unsupported_versions;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
@Data
public class AdminUsersUnsupportedVersionsExportResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;
}