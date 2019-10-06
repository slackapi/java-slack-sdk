package com.github.seratch.jslack.lightning;

import com.github.seratch.jslack.Slack;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppConfig {

    public static final class EnvVariableName {
        private EnvVariableName() {
        }

        public static final String SLACK_BOT_TOKEN = "SLACK_BOT_TOKEN";
        public static final String SLACK_SIGNING_SECRET = "SLACK_SIGNING_SECRET";
    }

    @Builder.Default
    private Slack slack = Slack.getInstance();
    @Builder.Default
    private String singleTeamBotToken = System.getenv(EnvVariableName.SLACK_BOT_TOKEN);
    @Builder.Default
    private String signingSecret = System.getenv(EnvVariableName.SLACK_SIGNING_SECRET);
}
