package com.slack.api.methods.request.apps.icon;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.io.File;

/**
 * https://docs.slack.dev/reference/methods/apps.icon.set
 */
@Data
@Builder
public class AppsIconSetRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter. When called with a manager app token, this method can only set icons for apps that were created by that manager app.
     */
    private String token;

    /**
     * The ID of the app whose icon you want to set.
     */
    private String appId;

    /**
     * File contents via `multipart/form-data`
     */
    private File file;
    private byte[] fileData;

    /**
     * URL of a publicly hosted image
     */
    private String url;

}
