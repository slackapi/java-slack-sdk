package com.slack.api.bolt.context.builtin;

import com.slack.api.app_backend.views.InputBlockResponseSender;
import com.slack.api.app_backend.views.payload.ViewSubmissionPayload;
import com.slack.api.app_backend.views.response.ViewSubmissionResponse;
import com.slack.api.bolt.context.Context;
import com.slack.api.bolt.context.InputBlockRespondUtility;
import com.slack.api.bolt.response.Responder;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.util.BuilderConfigurator;
import com.slack.api.model.view.View;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode
public class ViewSubmissionContext extends Context implements InputBlockRespondUtility {

    private List<ViewSubmissionPayload.ResponseUrl> responseUrls;
    private Responder responder;

    public ViewSubmissionContext() {
    }

    public Response ack(ViewSubmissionResponse response) {
        return Response.json(200, response);
    }

    public Response ack(
            BuilderConfigurator<ViewSubmissionResponse.ViewSubmissionResponseBuilder> builder) {
        return ack(builder.configure(ViewSubmissionResponse.builder()).build());
    }

    public Response ackWithErrors(Map<String, String> errors) {
        ViewSubmissionResponse response = ViewSubmissionResponse.builder()
                .responseAction("errors")
                .errors(errors)
                .build();
        return ack(response);
    }

    public Response ackWithUpdate(View view) {
        return ack("update", view);
    }

    public Response ackWithUpdate(String view) {
        return ack("update", view);
    }

    public Response ackWithPush(View view) {
        return ack("push", view);
    }

    public Response ackWithPush(String view) {
        return ack("push", view);
    }

    public Response ack(String responseAction, View view) {
        ViewSubmissionResponse response = ViewSubmissionResponse.builder()
                .responseAction(responseAction)
                .view(view)
                .build();
        return ack(response);
    }

    public Response ack(String responseAction, String view) {
        ViewSubmissionResponse response = ViewSubmissionResponse.builder()
                .responseAction(responseAction)
                .viewAsString(view)
                .build();
        return ack(response);
    }

    @Override
    public InputBlockResponseSender getResponder(String blockId, String actionId) {
        if (responseUrls != null) {
            for (ViewSubmissionPayload.ResponseUrl url : responseUrls) {
                if (url.getBlockId().equals(blockId) && url.getActionId().equals(actionId)) {
                    return new InputBlockResponseSender(getSlack(), url.getResponseUrl());
                }
            }
        }
        throw new IllegalArgumentException("A response_url was not found for block_id: " + blockId + " & action_id: " + actionId);
    }

    @Override
    public InputBlockResponseSender getFirstResponder() {
        List<ViewSubmissionPayload.ResponseUrl> urls = getResponseUrls();
        if (urls == null || urls.size() == 0) {
            return null;
        } else {
            if (urls.size() > 1) {
                // NOTE: As of March 2020, response_url_enabled field can be used on a single block element in a view.
                // That said, the payload can have multiple URLs here. The warning message is just for possible changes in the future.
                String warnMessage = "You have " + urls.size() + " `response_url`s in the payload. " +
                        "Context#getFirstResponder() and #respond(...) work but always use the first URL. " +
                        "Consider using Context#getResponder(blockId, actionId).send(...) instead.";
                getLogger().warn(warnMessage);
            }
            return new InputBlockResponseSender(getSlack(), urls.get(0).getResponseUrl());
        }
    }
}
