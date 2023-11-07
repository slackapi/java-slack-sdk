package com.slack.api.methods.request.apps.manifest;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.AppManifest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppsManifestUpdateRequest implements SlackApiRequest {

    private String token;

    private AppManifest manifest;
    private String manifestAsString;
    private String appId;
}