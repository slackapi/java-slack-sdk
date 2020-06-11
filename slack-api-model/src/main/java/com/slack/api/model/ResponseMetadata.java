package com.slack.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

/**
 * An attribute included within the response of the newest Slack APIs which contains a {@code next_cursor}
 * value.  By using this value as a cursor parameter in a subsequent request, along with limit, you may
 * navigate through the collection page by virtual page.
 */
@Data
public class ResponseMetadata {

    @SerializedName("next_cursor")
    private String nextCursor;

    // e.g., [ERROR] must be less than 100 [json-pointer:/limit]
    private List<String> messages;
    // e.g., method_deprecated
    private List<String> warnings;
}
