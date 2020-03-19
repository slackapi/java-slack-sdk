package com.slack.api.bolt.request.builtin;

import com.slack.api.app_backend.views.payload.ViewSubmissionPayload;
import com.slack.api.bolt.context.builtin.ViewSubmissionContext;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.RequestType;
import com.slack.api.util.json.GsonFactory;
import lombok.ToString;

@ToString(callSuper = true)
public class ViewSubmissionRequest extends Request<ViewSubmissionContext> {

    private final String requestBody;
    private final RequestHeaders headers;
    private final ViewSubmissionPayload payload;

    public ViewSubmissionRequest(
            String requestBody,
            String payloadBody,
            RequestHeaders headers) {
        this.requestBody = requestBody;
        this.headers = headers;
        this.payload = GsonFactory.createSnakeCase().fromJson(payloadBody, ViewSubmissionPayload.class);

        getContext().setEnterpriseId(payload.getTeam().getEnterpriseId());
        getContext().setTeamId(payload.getTeam().getId());
        getContext().setRequestUserId(payload.getUser().getId());
        getContext().setResponseUrls(payload.getResponseUrls());
    }

    private ViewSubmissionContext context = new ViewSubmissionContext();

    @Override
    public ViewSubmissionContext getContext() {
        return context;
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.ViewSubmission;
    }

    @Override
    public String getRequestBodyAsString() {
        return requestBody;
    }

    @Override
    public RequestHeaders getHeaders() {
        return this.headers;
    }

    @Override
    public String getResponseUrl() {
        if (payload.getResponseUrls() == null || payload.getResponseUrls().size() == 0) {
            return null;
        } else {
            int numOfResponseUrls = payload.getResponseUrls().size();
            if (numOfResponseUrls > 1) {
                // NOTE: As of March 2020, response_url_enabled field can be used on a single block element in a view.
                // That said, the payload can have multiple URLs here. The warning message is just for possible changes in the future.
                String warnMessage = "You have " + numOfResponseUrls + " `response_url`s in the payload. " +
                        "ViewSubmissionRequest#getResponseUrl() always returns the first one.";
                getContext().getLogger().warn(warnMessage);
            }
            return payload.getResponseUrls().get(0).getResponseUrl();
        }
    }

    public ViewSubmissionPayload getPayload() {
        return payload;
    }
}
