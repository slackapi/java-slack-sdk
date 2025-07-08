package com.slack.api.methods.request.calls.participants;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.CallParticipant;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/calls.participants.remove
 */
@Data
@Builder
public class CallsParticipantsRemoveRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * id returned by the calls.add method.
     */
    private String id;

    /**
     * The list of users to register as participants in the Call.
     */
    private List<CallParticipant> users;

}