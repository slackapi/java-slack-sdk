package test_with_remote_apis.audit;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.audit.Actions;
import com.slack.api.audit.AuditApiCompletionException;
import com.slack.api.audit.AuditApiException;
import com.slack.api.audit.request.LogsRequest;
import com.slack.api.audit.response.ActionsResponse;
import com.slack.api.audit.response.LogsResponse;
import com.slack.api.audit.response.SchemasResponse;
import com.slack.api.util.json.GsonFactory;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import util.ObjectInitializer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

// required scope - auditlogs:read
@Slf4j
public class ApiTest {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    String orgAdminUserToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN);

    public static void main(String[] args) throws Exception {
        regenerateLogsSampleJSONData("json-logs/samples/audit/v1/logs.json");
    }

    public static void regenerateLogsSampleJSONData(String path) throws Exception {
        // Re-generates new sample JSON with the valid data classes
        Path filePath = Paths.get(path);
        LogsResponse logs = ObjectInitializer.initProperties(new LogsResponse());

        LogsResponse.Entry entry = ObjectInitializer.initProperties(new LogsResponse.Entry());

        LogsResponse.Actor actor = ObjectInitializer.initProperties(new LogsResponse.Actor());
        entry.setActor(actor);

        LogsResponse.Entity entity = ObjectInitializer.initProperties(new LogsResponse.Entity());
        entity.getApp().setScopes(Arrays.asList(""));
        entity.getChannel().setTeamsSharedWith(Arrays.asList(""));
        entity.getBarrier().setBarrieredFromUsergroups(Arrays.asList(""));
        entity.getBarrier().setRestrictedSubjects(Arrays.asList(""));
        entry.setEntity(entity);

        LogsResponse.Context context = ObjectInitializer.initProperties(new LogsResponse.Context());
        entry.setContext(context);

        LogsResponse.Details details = ObjectInitializer.initProperties(new LogsResponse.Details());
        details.setNewValue(null); // as this property is dynamic, an example is not usable
        details.setPreviousValue(null); // as this property is dynamic, an example is not usable
        details.setScopes(Arrays.asList(""));
        details.setBotScopes(Arrays.asList(""));
        details.setNewScopes(Arrays.asList(""));
        details.setPreviousScopes(Arrays.asList(""));
        details.setOldScopes(Arrays.asList(""));
        details.setChannels(Arrays.asList(""));
        LogsResponse.Permission permission = ObjectInitializer.initProperties(new LogsResponse.Permission());
        permission.setScopes(Arrays.asList(""));
        details.setPermissions(Arrays.asList(permission));
        details.setBarrieredFromUsergroupIds(Arrays.asList(""));
        details.setRestrictedSubjects(Arrays.asList(""));
        LogsResponse.ConversationPref conversationPref = new LogsResponse.ConversationPref();
        conversationPref.setType(Arrays.asList(""));
        conversationPref.setUser(Arrays.asList(""));
        details.setWhoCanPost(conversationPref);
        details.setCanThread(conversationPref);
        entry.setDetails(details);

        logs.setEntries(Arrays.asList(entry));

        SlackConfig slackConfig = new SlackConfig();
        slackConfig.setPrettyResponseLoggingEnabled(true);
        String json = GsonFactory.createSnakeCase(slackConfig).toJson(logs);
        Files.write(filePath, json.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void getSchemas() throws IOException, AuditApiException {
        if (orgAdminUserToken != null) {
            {
                SchemasResponse response = slack.audit(orgAdminUserToken).getSchemas();
                assertThat(response, is(notNullValue()));
            }
            {
                SchemasResponse response = slack.audit(orgAdminUserToken).getSchemas(req -> req);
                assertThat(response, is(notNullValue()));
            }
        }
    }

    @Test(expected = IllegalStateException.class)
    public void getSchemas_missingToken() throws IOException, AuditApiException {
        SchemasResponse response = slack.audit().getSchemas();
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void getSchemas_dummy() throws IOException, AuditApiException {
        SchemasResponse response = slack.audit("dummy").getSchemas();
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void getActions() throws IOException, AuditApiException {
        if (orgAdminUserToken != null) {
            {
                ActionsResponse response = slack.audit(orgAdminUserToken).getActions();
                assertThat(response, is(notNullValue()));
            }
            {
                ActionsResponse response = slack.audit(orgAdminUserToken).getActions(req -> req);
                assertThat(response, is(notNullValue()));
            }
        }
    }

    @Test(expected = IllegalStateException.class)
    public void getActions_missingToken() throws IOException, AuditApiException {
        ActionsResponse response = slack.audit().getActions();
        assertThat(response, is(notNullValue()));
    }

    public static List<String> getAllPublicStaticFieldValues(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> Modifier.isStatic(f.getModifiers()) && Modifier.isPublic(f.getModifiers()))
                .map(f -> {
                    try {
                        return String.valueOf(f.get(clazz));
                    } catch (IllegalAccessException e) {
                        log.warn("Failed to get value from {}.{}", clazz.getCanonicalName(), f.getName());
                        return null;
                    }
                }).collect(Collectors.toList());
    }

    @Test
    public void getActions_detectingNewOnes() throws IOException, AuditApiException {
        ActionsResponse response = slack.audit("dummy").getActions();
        assertThat(response, is(notNullValue()));

        ActionsResponse.Actions actions = response.getActions();
        List<String> appNames = getAllPublicStaticFieldValues(Actions.App.class);
        for (String action : actions.getApp()) {
            if (!appNames.contains(action)) {
                fail("Unknown action detected - " + action);
            }
        }
        List<String> userNames = getAllPublicStaticFieldValues(Actions.User.class);
        for (String action : actions.getUser()) {
            if (!userNames.contains(action)) {
                fail("Unknown action detected - " + action);
            }
        }
        List<String> channelNames = getAllPublicStaticFieldValues(Actions.Channel.class);
        for (String action : actions.getChannel()) {
            if (!channelNames.contains(action)) {
                fail("Unknown action detected - " + action);
            }
        }
        List<String> fileNames = getAllPublicStaticFieldValues(Actions.File.class);
        for (String action : actions.getFile()) {
            if (!fileNames.contains(action)) {
                fail("Unknown action detected - " + action);
            }
        }
        List<String> wsOrOrgNames = getAllPublicStaticFieldValues(Actions.WorkspaceOrOrg.class);
        for (String action : actions.getWorkspaceOrOrg()) {
            if (!wsOrOrgNames.contains(action)) {
                fail("Unknown action detected - " + action);
            }
        }
        List<String> slackCliNames = getAllPublicStaticFieldValues(Actions.SlackCLI.class);
        for (String action : actions.getSlackCLI()) {
            if (!slackCliNames.contains(action)) {
                fail("Unknown action detected - " + action);
            }
        }
        List<String> wfbNames = getAllPublicStaticFieldValues(Actions.WorkflowBuilder.class);
        for (String action : actions.getWorkflowBuilder()) {
            if (!wfbNames.contains(action)) {
                fail("Unknown action detected - " + action);
            }
        }
        List<String> huddleNames = getAllPublicStaticFieldValues(Actions.Huddle.class);
        for (String action : actions.getHuddle()) {
            if (!huddleNames.contains(action)) {
                fail("Unknown action detected - " + action);
            }
        }
        List<String> barrierNames = getAllPublicStaticFieldValues(Actions.Barrier.class);
        for (String action : actions.getBarrier()) {
            if (!barrierNames.contains(action)) {
                fail("Unknown action detected - " + action);
            }
        }
        List<String> roleNames = getAllPublicStaticFieldValues(Actions.Role.class);
        for (String action : actions.getRole()) {
            if (!roleNames.contains(action)) {
                fail("Unknown action detected - " + action);
            }
        }
        List<String> subteamNames = getAllPublicStaticFieldValues(Actions.Subteam.class);
        for (String action : actions.getSubteam()) {
            if (!subteamNames.contains(action)) {
                fail("Unknown action detected - " + action);
            }
        }
        List<String> accountTypeRoleNames = getAllPublicStaticFieldValues(Actions.AccountTypeRole.class);
        for (String action : actions.getAccountTypeRole()) {
            if (!accountTypeRoleNames.contains(action)) {
                fail("Unknown action detected - " + action);
            }
        }
        List<String> appApprovalAutomationRuleNames = getAllPublicStaticFieldValues(Actions.AppApprovalAutomationRule.class);
        for (String action : actions.getAppApprovalAutomationRule()) {
            if (!appApprovalAutomationRuleNames.contains(action)) {
                fail("Unknown action detected - " + action);
            }
        }
        List<String> workflowV2Names = getAllPublicStaticFieldValues(Actions.WorkflowV2.class);
        for (String action : actions.getWorkflowV2()) {
            if (!workflowV2Names.contains(action)) {
                fail("Unknown action detected - " + action);
            }
        }
        List<String> canvasNames = getAllPublicStaticFieldValues(Actions.Canvas.class);
        for (String action : actions.getCanvas()) {
            if (!canvasNames.contains(action)) {
                fail("Unknown action detected - " + action);
            }
        }
        List<String> functionNames = getAllPublicStaticFieldValues(Actions.Function.class);
        for (String action : actions.getFunction()) {
            if (!functionNames.contains(action)) {
                fail("Unknown action detected - " + action);
            }
        }
        List<String> salesElevateNames = getAllPublicStaticFieldValues(Actions.SalesElevate.class);
        for (String action : actions.getSalesElevate()) {
            if (!salesElevateNames.contains(action)) {
                fail("Unknown action detected - " + action);
            }
        }
    }

    @Test
    public void getLogs() throws IOException, AuditApiException {
        if (orgAdminUserToken != null) {
            LogsResponse response = slack.audit(orgAdminUserToken).getLogs(req ->
                    req.oldest(1521214343).action(Actions.User.user_login).limit(10));
            assertThat(response, is(notNullValue()));
        }
    }

    @Test(expected = IllegalStateException.class)
    public void getLogs_missingToken() throws IOException, AuditApiException {
        LogsResponse response = slack.audit().getLogs(req ->
                req.oldest(1521214343).action(Actions.User.user_login).limit(10));
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void getLogs_dummy() throws IOException {
        try {
            slack.audit("dummy").getLogs(req ->
                    req.oldest(1521214343).action(Actions.User.user_login).limit(10));
            fail();
        } catch (AuditApiException e) {
            assertThat(e.getMessage(), is("status: 401, no response body"));
            assertThat(e.getResponse().code(), is(401));
            assertThat(e.getError(), is(nullValue()));
        }
    }

    @Test
    public void getLogs_paginated() throws IOException, AuditApiException {
        if (orgAdminUserToken != null) {
            LogsResponse response = slack.audit(orgAdminUserToken).getLogs(req -> req
                    .oldest(1521214343)
                    .action(Actions.User.user_login)
                    .limit(1)
            );
            String cursor = response.getResponseMetadata().getNextCursor();
            response = slack.audit(orgAdminUserToken).getLogs(req -> req
                    .oldest(1521214343)
                    .action(Actions.User.user_login)
                    .limit(1)
                    .cursor(cursor)
            );
            assertThat(cursor, not(equalTo(response.getResponseMetadata().getNextCursor())));
            assertThat(response, is(notNullValue()));
        }
    }

    @Test
    public void getLogs_all_actions() throws Exception {
        if (orgAdminUserToken != null) {
            verifyAllActions(orgAdminUserToken, Actions.WorkflowV2.class);
            verifyAllActions(orgAdminUserToken, Actions.Canvas.class);
            verifyAllActions(orgAdminUserToken, Actions.SlackCLI.class);
            verifyAllActions(orgAdminUserToken, Actions.WorkspaceOrOrg.class);
            verifyAllActions(orgAdminUserToken, Actions.User.class);
            verifyAllActions(orgAdminUserToken, Actions.File.class);
            verifyAllActions(orgAdminUserToken, Actions.Channel.class);
            verifyAllActions(orgAdminUserToken, Actions.Message.class);
            verifyAllActions(orgAdminUserToken, Actions.WorkflowBuilder.class);
            verifyAllActions(orgAdminUserToken, Actions.App.class);
            verifyAllActions(orgAdminUserToken, Actions.Barrier.class);
            verifyAllActions(orgAdminUserToken, Actions.Huddle.class);
            verifyAllActions(orgAdminUserToken, Actions.Anomaly.class);
            verifyAllActions(orgAdminUserToken, Actions.Subteam.class);
            verifyAllActions(orgAdminUserToken, Actions.Role.class);
            verifyAllActions(orgAdminUserToken, Actions.AccountTypeRole.class);
            verifyAllActions(orgAdminUserToken, Actions.AppApprovalAutomationRule.class);

            // As all the properties are available in LogsResponse class,
            // we'll re-generate the sample JSON file.
            regenerateLogsSampleJSONData("../json-logs/samples/audit/v1/logs.json");
        }
    }

    static void verifyAllActions(String token, Class<?> clazz) throws Exception {
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> Modifier.isStatic(f.getModifiers()) && Modifier.isPublic(f.getModifiers()))
                .collect(toList());
        for (Field f : fields) {
            String action = String.valueOf(f.get(clazz));
            try {
                LogsResponse response = slack.auditAsync(token).getLogs(req -> req.limit(500).action(action)).get();
                assertThat(response.getError(), is(nullValue()));
            } catch (ExecutionException e) {
                if (e.getCause() != null && e.getCause() instanceof AuditApiCompletionException) {
                    AuditApiCompletionException apiEx = ((AuditApiCompletionException) e.getCause());
                    if (apiEx.getAuditApiException() != null && apiEx.getAuditApiException().getResponse() != null) {
                        if (apiEx.getAuditApiException().getResponse().code() == 400) {
                            log.info("{} seems to be no longer supported", action);
                        } else {
                            throw apiEx;
                        }
                    } else {
                        throw apiEx;
                    }
                } else {
                    throw e;
                }
            }
        }
    }

    @Test
    public void getLogs_issue_525()
            throws IOException, AuditApiException {
        if (orgAdminUserToken != null) {
            LogsResponse response = slack.audit(orgAdminUserToken).getLogs(req -> req
                    .limit(10)
                    .action(Actions.WorkspaceOrOrg.pref_enterprise_default_channels));

            assertThat(response.getEntries().size(), is(not(0)));

            for (LogsResponse.Entry entry : response.getEntries()) {
                assertThat(entry.getDetails().getNewValue(), is(notNullValue()));
                assertThat(entry.getDetails().getNewValue().getStringValues(), is(notNullValue()));
                assertThat(entry.getDetails().getNewValue().getNamedStringValues(), is(nullValue()));
            }

            response = slack.audit(orgAdminUserToken).getLogs(req -> req
                    .limit(10)
                    .action(Actions.WorkspaceOrOrg.pref_who_can_manage_ext_shared_channels));

            assertThat(response.getEntries().size(), is(not(0)));

            for (LogsResponse.Entry entry : response.getEntries()) {
                assertThat(entry.getDetails().getNewValue(), is(notNullValue()));
                assertThat(entry.getDetails().getNewValue().getStringValues(), is(nullValue()));
                assertThat(entry.getDetails().getNewValue().getNamedStringValues(), is(notNullValue()));
            }
        }
    }

    @Test
    public void attachBody() throws Exception {
        if (orgAdminUserToken != null) {
            // false (default)
            LogsResponse response = slack.audit(orgAdminUserToken).getLogs(req -> req
                    .limit(1)
                    .action(Actions.User.user_login)
            );
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.getEntries().size(), is(not(0)));
            assertThat(response.getRawBody(), is(nullValue())); // null
            // true
            response = slack.audit(orgAdminUserToken).attachRawBody(true).getLogs(req -> req
                    .limit(1)
                    .action(Actions.User.user_login)
            );
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.getEntries().size(), is(not(0)));
            assertThat(response.getRawBody(), is(notNullValue())); // non-null
        }
    }

    @Ignore
    @Test
    public void demonstrateRateLimitedError() throws ExecutionException, InterruptedException, IOException {
        LogsRequest req = LogsRequest.builder()
                .limit(1)
                .action(Actions.User.user_login)
                .build();
        while (true) {
            try {
                slack.audit(orgAdminUserToken).attachRawBody(true).getLogs(req);
            } catch (AuditApiException e) {
                if (e.getResponse().code() == 429) {
                    break;
                }
            }
        }
        while (true) {
            slack.auditAsync(orgAdminUserToken).attachRawBody(true).getLogs(req).get();
        }
    }

}
