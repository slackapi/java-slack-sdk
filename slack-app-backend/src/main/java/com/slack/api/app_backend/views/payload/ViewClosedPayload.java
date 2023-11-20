package com.slack.api.app_backend.views.payload;

import com.slack.api.model.event.FunctionExecutedEvent;
import com.slack.api.model.view.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @see <a href="https://api.slack.com/block-kit/surfaces/modals">Modals</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViewClosedPayload {
    public static final String TYPE = "view_closed";
    private final String type = TYPE;
    private Enterprise enterprise;
    private Team team;
    private User user;
    private String apiAppId;
    private String token;
    private View view;
    private boolean isEnterpriseInstall;
    private boolean isCleared;

    @Data
    public static class Enterprise {
        private String id;
        private String name;
    }

    @Data
    public static class Team {
        private String id;
        private String domain;
        private String enterpriseId;
        private String enterpriseName;
    }

    @Data
    public static class User {
        private String id;
        private String username;
        private String name;
        private String teamId;
    }

    private String botAccessToken; // for remote function's interactivity
    private FunctionData functionData; // for remote function's interactivity
    private Interactivity interactivity; // for remote function's interactivity

    @Data
    public static class FunctionData {
        private String executionId;
        private Function function;
        private Map<String, FunctionExecutedEvent.InputValue> inputs;
    }

    @Data
    public static class Function {
        private String callbackId;
    }

    @Data
    public static class Interactivity {
        private String interactivityPointer; // you can use this in the same way with trigger_id
        private Interactor interactor;
    }
    @Data
    public static class Interactor {
        private String id;
        private String secret;
    }
}