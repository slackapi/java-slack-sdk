package com.slack.api.methods.request.calls.participants;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.CallParticipant;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/methods/calls.participants.add
 */
@Data
@Builder
public class CallsParticipantsAddRequest implements SlackApiRequest {

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