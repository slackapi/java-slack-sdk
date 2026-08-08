package com.slack.api.methods.request.blocks;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlocksValidateRequest implements SlackApiRequest {

    private String token;

    /**
     * A JSON-encoded string of an array of blocks to validate.
     */
    private String blocks;

    /**
     * A JSON-encoded string of a message payload to validate.
     */
    private String message;

    /**
     * A JSON-encoded string of a view payload to validate.
     */
    private String view;
}
