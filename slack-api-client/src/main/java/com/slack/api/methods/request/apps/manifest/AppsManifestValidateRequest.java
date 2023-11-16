package com.slack.api.methods.request.apps.manifest;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.manifest.AppManifestParams;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppsManifestValidateRequest implements SlackApiRequest {

    private String token;

    private AppManifestParams manifest;
    private String manifestAsString;
    private String appId;
}