package test_locally.docs;

import com.slack.api.Slack;
import com.slack.api.audit.Actions;
import com.slack.api.audit.AuditApiException;
import com.slack.api.audit.AuditClient;
import com.slack.api.audit.response.ActionsResponse;
import com.slack.api.audit.response.LogsResponse;
import com.slack.api.audit.response.SchemasResponse;

import java.io.IOException;

public class AuditLogsApiTest {

    public void getLogs() throws IOException, AuditApiException {
        Slack slack = Slack.getInstance();
        String token = System.getenv("SLACK_ADMIN_ACCESS_TOKN"); // `auditlogs:read` scope required
        AuditClient audit = slack.audit(token);

        LogsResponse response = audit.getLogs(req -> req
                .oldest(1521214343) // Unix timestamp of the least recent audit event to include (inclusive)
                .action(Actions.User.user_login) // A team member logged in
                .limit(10) // Number of results to optimistically return
        );
    }

    public void getSchemas() throws IOException, AuditApiException {
        Slack slack = Slack.getInstance();
        String token = System.getenv("SLACK_ADMIN_ACCESS_TOKN"); // `auditlogs:read` scope required
        AuditClient audit = slack.audit(token);

        SchemasResponse response = audit.getSchemas();
    }

    public void getActions() throws IOException, AuditApiException {
        Slack slack = Slack.getInstance();
        String token = System.getenv("SLACK_ADMIN_ACCESS_TOKN"); // `auditlogs:read` scope required
        AuditClient audit = slack.audit(token);

        ActionsResponse response = audit.getActions();
    }

}
