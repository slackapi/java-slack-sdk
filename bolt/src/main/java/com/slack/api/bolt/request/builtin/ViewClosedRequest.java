package com.slack.api.bolt.request.builtin;

import com.slack.api.app_backend.views.payload.ViewClosedPayload;
import com.slack.api.bolt.context.builtin.DefaultContext;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.RequestType;
import com.slack.api.util.json.GsonFactory;
import lombok.ToString;

@ToString(callSuper = true)
public class ViewClosedRequest extends Request<DefaultContext> {

    private final String requestBody;
    private final RequestHeaders headers;
    private final ViewClosedPayload payload;

    public ViewClosedRequest(
            String requestBody,
            String payloadBody,
            RequestHeaders headers) {
        this.requestBody = requestBody;
        this.headers = headers;
        this.payload = GsonFactory.createSnakeCase().fromJson(payloadBody, ViewClosedPayload.class);

        if (payload.getEnterprise() != null) {
            getContext().setEnterpriseId(payload.getEnterprise().getId());
        } else if (payload.getTeam() != null) {
            getContext().setEnterpriseId(payload.getTeam().getEnterpriseId());
        }
        if (payload.getView() != null && payload.getView().getAppInstalledTeamId() != null) {
            // view_submission payloads can have `view.app_installed_team_id` when a modal view that was opened
            // in a different workspace via some operations inside a Slack Connect channel.
            // Note that the same for enterprise_id does not exist. When you need to know the enterprise_id as well,
            // you have to run some query toward your InstallationStore to know the org where the team_id belongs to.
            getContext().setTeamId(payload.getView().getAppInstalledTeamId());
        } else if (payload.getTeam() != null && payload.getTeam().getId() != null) {
            getContext().setTeamId(payload.getTeam().getId());
        } else if (payload.getView() != null && payload.getView().getTeamId() != null) {
            getContext().setTeamId(payload.getView().getTeamId());
        } else if (payload.getUser() != null && payload.getUser().getId() != null) {
            getContext().setTeamId(payload.getUser().getTeamId());
        }
        getContext().setRequestUserId(payload.getUser().getId());
        getContext().setFunctionBotAccessToken(payload.getBotAccessToken());
    }

    private DefaultContext context = new DefaultContext();

    @Override
    public DefaultContext getContext() {
        return context;
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.ViewClosed;
    }

    @Override
    public String getRequestBodyAsString() {
        return requestBody;
    }

    @Override
    public RequestHeaders getHeaders() {
        return this.headers;
    }

    public ViewClosedPayload getPayload() {
        return payload;
    }

    @Override
    public String getResponseUrl() {
        return null;
    }
}
