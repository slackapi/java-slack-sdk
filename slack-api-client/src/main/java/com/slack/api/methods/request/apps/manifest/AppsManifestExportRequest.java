package com.slack.api.methods.request.apps.manifest;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppsManifestExportRequest implements SlackApiRequest {

    private String token;

    private String appId;
}