package test_with_remote_apis.audit;

import com.slack.api.Slack;
import com.slack.api.audit.Actions;
import com.slack.api.audit.AuditApiException;
import com.slack.api.audit.response.ActionsResponse;
import com.slack.api.audit.response.LogsResponse;
import com.slack.api.audit.response.SchemasResponse;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
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
    public void getActions_dummy() throws IOException, AuditApiException {
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
            verifyAllActions(orgAdminUserToken, Actions.WorkspaceOrOrg.class);
            verifyAllActions(orgAdminUserToken, Actions.User.class);
            verifyAllActions(orgAdminUserToken, Actions.File.class);
            verifyAllActions(orgAdminUserToken, Actions.Channel.class);
            verifyAllActions(orgAdminUserToken, Actions.App.class);
        }
    }

    static void verifyAllActions(String token, Class<?> clazz) throws Exception {
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> Modifier.isStatic(f.getModifiers()) && Modifier.isPublic(f.getModifiers()))
                .collect(toList());
        for (Field f : fields) {
            String action = String.valueOf(f.get(clazz));
            try {
                LogsResponse response = slack.audit(token).getLogs(req -> req.limit(500).action(action));
                assertThat(response.getError(), is(nullValue()));
            } catch (AuditApiException e) {
                if (e.getResponse().code() == 400) {
                    log.info("{} seems to be no longer supported", action);
                }
            }
            Thread.sleep(30); // To avoid rate limiting errors
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

}
