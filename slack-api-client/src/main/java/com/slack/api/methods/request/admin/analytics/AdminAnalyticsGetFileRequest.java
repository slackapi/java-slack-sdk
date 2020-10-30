package com.slack.api.methods.request.admin.analytics;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/admin.analytics.getFile
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

}
