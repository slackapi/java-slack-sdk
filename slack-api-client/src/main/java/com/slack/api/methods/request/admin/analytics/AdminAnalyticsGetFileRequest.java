package com.slack.api.methods.request.admin.analytics;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/admin.analytics.getFile
 */
@Data
@Builder
public class AdminAnalyticsGetFileRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * Date to retrieve the analytics data for, expressed as YYYY-MM-DD in UTC.
     */
    private String date;

    /**
     * The type of analytics to retrieve. The options are currently limited to member.
     */
    private String type;

    /**
     * Retrieve metadata for the type of analytics indicated.
     * Can be used only with type set to public_channel analytics.
     * See detail below. Omit the date parameter when using this argument.
     * Default: false
     */
    private Boolean metadataOnly;

}
