package test_with_remote_apis.scim;

import com.slack.api.Slack;
import com.slack.api.scim.SCIMApiCompletionException;
import com.slack.api.scim.SCIMApiException;
import com.slack.api.scim.model.Group;
import com.slack.api.scim.model.User;
import com.slack.api.scim.request.GroupsPatchRequest;
import com.slack.api.scim.response.*;
import com.slack.api.util.thread.ExecutorServiceFactory;
import config.Constants;
import config.SlackTestConfig;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

// required scope - admin
public class AsyncApiTest {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    String orgAdminToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN);

    private UsersCreateResponse createNewUser() throws ExecutionException, InterruptedException {
        String userName = "user" + System.currentTimeMillis();
        User newUser = new User();
        newUser.setName(new User.Name());
        newUser.getName().setGivenName("Kazuhiro");
        newUser.getName().setFamilyName("Sera");
        newUser.setUserName(userName);
        User.Email email = new User.Email();
        email.setValue("seratch+" + System.currentTimeMillis() + "@gmail.com");
        newUser.setEmails(Arrays.asList(email));
        return slack.scimAsync(orgAdminToken).createUser(req -> req.user(newUser)).get();
    }

    @Test
    public void getServiceProviderConfigs() throws ExecutionException, InterruptedException {
        if (orgAdminToken != null) {
            ServiceProviderConfigsGetResponse response = slack.scimAsync(orgAdminToken)
                    .getServiceProviderConfigs(req -> req).get();
            assertThat(response.getAuthenticationSchemes(), is(notNullValue()));
        }
    }

    @Test
    public void getServiceProviderConfigs_dummy() throws ExecutionException, InterruptedException {
        ServiceProviderConfigsGetResponse response = slack.scimAsync("dummy")
                .getServiceProviderConfigs(req -> req).get();
        assertThat(response.getAuthenticationSchemes(), is(notNullValue()));
    }

    @Test
    public void searchAndReadUser() throws ExecutionException, InterruptedException {
        if (orgAdminToken != null) {
            {
                UsersSearchResponse users = slack.scimAsync(orgAdminToken).searchUsers(req -> req.count(1000)).get();
                final String userId = users.getResources().get(0).getId();
                UsersReadResponse read = slack.scimAsync(orgAdminToken).readUser(req -> req.id(userId)).get();
                assertThat(read.getId(), is(userId));
            }
            {
                // pagination
                UsersSearchResponse users = slack.scimAsync(orgAdminToken)
                        .searchUsers(req -> req.count(1).startIndex(2)).get();
                assertThat(users.getItemsPerPage(), is(1));
                assertThat(users.getResources().size(), is(1));
                assertThat(users.getStartIndex(), is(2));
            }
        }
    }

    @Test
    public void searchUser_dummy() throws IOException, ExecutionException, InterruptedException {
        try {
            slack.scimAsync("dummy").searchUsers(req -> req.count(1000)).get();
        } catch (Exception _e) {
            SCIMApiException e = ((SCIMApiCompletionException) _e.getCause()).getScimApiException();
            assertThat(e.getMessage(), is("status: 401, description: invalid_authentication"));
            assertThat(e.getError(), is(notNullValue()));
            assertThat(e.getError().getErrors().getCode(), is(401));
            assertThat(e.getError().getErrors().getDescription(), is("invalid_authentication"));
        }
    }

    @Test
    public void readUser_dummy() {
        try {
            slack.scimAsync("dummy").readUser(req -> req.id("U12345678")).get();
        } catch (Exception _e) {
            SCIMApiException e = ((SCIMApiCompletionException) _e.getCause()).getScimApiException();
            assertThat(e.getMessage(), is("status: 401, description: invalid_authentication"));
            assertThat(e.getError(), is(notNullValue()));
            assertThat(e.getError().getErrors().getCode(), is(401));
            assertThat(e.getError().getErrors().getDescription(), is("invalid_authentication"));
        }
    }

    @Test
    public void createAndDeleteUser() throws Exception {
        if (orgAdminToken != null) {
            UsersCreateResponse creation = createNewUser();
            assertThat(creation.getId(), is(notNullValue()));

            UsersReadResponse readResp = slack.scimAsync(orgAdminToken)
                    .readUser(req -> req.id(creation.getId())).get();
            assertThat(readResp.getId(), is(creation.getId()));
            assertThat(readResp.getUserName(), is(creation.getUserName()));

            try {
                // https://api.slack.com/scim#filter
                // filter query matching the created data
                String userName = creation.getUserName();
                UsersSearchResponse searchResp = slack.scimAsync(orgAdminToken)
                        .searchUsers(req -> req.count(1).filter("userName eq \"" + userName + "\"")).get();
                assertThat(searchResp.getItemsPerPage(), is(1));
                assertThat(searchResp.getResources().size(), is(1));
                assertThat(searchResp.getResources().get(0).getId(), is(creation.getId()));
                assertThat(searchResp.getResources().get(0).getUserName(), is(creation.getUserName()));

                String originalUserName = creation.getUserName();

                User user = new User();
                user.setUserName(originalUserName + "_ed");
                slack.scimAsync(orgAdminToken).patchUser(req -> req.id(creation.getId()).user(user));

                Thread.sleep(500L);
                readResp = slack.scimAsync(orgAdminToken).readUser(req -> req.id(creation.getId())).get();
                assertThat(readResp.getId(), is(creation.getId()));
                assertThat(readResp.getUserName(), is(originalUserName + "_ed"));

                User modifiedUser = readResp;
                modifiedUser.setUserName(originalUserName + "_rv");
                modifiedUser.setNickName(originalUserName + "_rv"); // required
                slack.scimAsync(orgAdminToken).updateUser(req -> req.id(modifiedUser.getId()).user(modifiedUser));

                Thread.sleep(500L);
                readResp = slack.scimAsync(orgAdminToken).readUser(req -> req.id(modifiedUser.getId())).get();
                assertThat(readResp.getId(), is(creation.getId()));
                assertThat(readResp.getUserName(), is(originalUserName + "_rv"));

            } finally {
                UsersDeleteResponse deletion = slack.scimAsync(orgAdminToken)
                        .deleteUser(req -> req.id(creation.getId())).get();
                assertThat(deletion, is(nullValue()));
                // can call twice
                UsersDeleteResponse deletion2 = slack.scimAsync(orgAdminToken)
                        .deleteUser(req -> req.id(creation.getId())).get();
                assertThat(deletion2, is(nullValue()));

                int counter = 0;
                UsersReadResponse supposedToBeDeleted = null;
                while (counter < 10) {
                    Thread.sleep(1000);
                    supposedToBeDeleted = slack.scimAsync(orgAdminToken)
                            .readUser(req -> req.id(creation.getId())).get();
                    if (!supposedToBeDeleted.getActive()) {
                        break;
                    }
                }
                assertThat(supposedToBeDeleted, is(notNullValue()));
                assertThat(supposedToBeDeleted.getActive(), is(false));

                // can call again
                UsersDeleteResponse deletion3 = slack.scimAsync(orgAdminToken)
                        .deleteUser(req -> req.id(creation.getId())).get();
                assertThat(deletion3, is(nullValue()));
            }
        }
    }

    @Test
    public void groups() throws ExecutionException, InterruptedException {
        if (orgAdminToken != null) {
            GroupsSearchResponse groups = slack.scimAsync(orgAdminToken)
                    .searchGroups(req -> req.count(1000)).get();
            if (groups.getResources().size() == 0) {
                Group newGroup = new Group();
                newGroup.setDisplayName("Test Group" + System.currentTimeMillis());

                Group.Member member = new Group.Member();
                UsersCreateResponse newUser = createNewUser();
                assertThat(newUser.getId(), is(notNullValue()));
                member.setValue(newUser.getId());
                newGroup.setMembers(Arrays.asList(member));

                GroupsCreateResponse createdGroup = slack.scimAsync(orgAdminToken)
                        .createGroup(req -> req.group(newGroup)).get();
                assertThat(createdGroup.getMembers().size(), is(1));
            }

            // pagination
            GroupsSearchResponse pagination = slack.scimAsync(orgAdminToken)
                    .searchGroups(req -> req.count(1)).get();
            assertThat(pagination.getResources().size(), is(1));

            Group group = groups.getResources().get(0);

            GroupsPatchRequest.GroupOperation op = new GroupsPatchRequest.GroupOperation();
            GroupsPatchRequest.MemberOperation memberOp = new GroupsPatchRequest.MemberOperation();
            User user = slack.scimAsync(orgAdminToken).searchUsers(req -> req.count(1)).get().getResources().get(0);
            memberOp.setValue(user.getId());
            memberOp.setOperation("delete");
            op.setMembers(Arrays.asList(memberOp));

            slack.scimAsync(orgAdminToken).patchGroup(req -> req.id(group.getId()).group(op));

            slack.scimAsync(orgAdminToken).updateGroup(req -> req.id(group.getId()).group(group));

            GroupsReadResponse read = slack.scimAsync(orgAdminToken).readGroup(req -> req.id(group.getId())).get();
            assertThat(read.getId(), is(group.getId()));
        }
    }

    @Test
    public void groupAndUsers() throws ExecutionException, InterruptedException {
        if (orgAdminToken != null) {
            Group newGroup = new Group();
            newGroup.setDisplayName("Test Group" + System.currentTimeMillis());
            UsersCreateResponse newUser = createNewUser();
            assertThat(newUser.getId(), is(notNullValue()));
            try {
                Group.Member member = new Group.Member();
                member.setValue(newUser.getId());
                newGroup.setMembers(Arrays.asList(member));
                GroupsCreateResponse createdGroup = slack.scimAsync(orgAdminToken)
                        .createGroup(req -> req.group(newGroup)).get();
                assertThat(createdGroup.getMembers().size(), is(1));
                try {
                    String userName = newUser.getUserName();
                    UsersSearchResponse searchResp = slack.scimAsync(orgAdminToken)
                            .searchUsers(req -> req.count(1).filter("userName eq \"" + userName + "\"")).get();
                    assertThat(searchResp.getResources().size(), is(1));
                    assertThat(searchResp.getResources().get(0).getGroups().size(), is(1));
                } finally {
                    GroupsDeleteResponse groupDeletion = slack.scimAsync(orgAdminToken)
                            .deleteGroup(req -> req.id(createdGroup.getId())).get();
                    assertThat(groupDeletion, is(nullValue()));
                }
            } finally {
                UsersDeleteResponse deletion = slack.scimAsync(orgAdminToken)
                        .deleteUser(req -> req.id(newUser.getId())).get();
                assertThat(deletion, is(nullValue()));
            }
        }
    }

    @Test
    public void groups_dummy() throws IOException, ExecutionException, InterruptedException {
        try {
            slack.scimAsync("dummy").searchGroups(req -> req.count(1000)).get();
        } catch (Exception _e) {
            SCIMApiException e = ((SCIMApiCompletionException) _e.getCause()).getScimApiException();
            assertThat(e.getMessage(), is("status: 401, description: invalid_authentication"));
            assertThat(e.getError(), is(notNullValue()));
            assertThat(e.getError().getErrors().getCode(), is(401));
            assertThat(e.getError().getErrors().getDescription(), is("invalid_authentication"));
        }
    }

    @Ignore
    @Test
    public void demonstrateRateLimitedErrors() throws Exception {
        Logger logger = LoggerFactory.getLogger(AsyncApiTest.class);
        int concurrency = 10;
        ExecutorService executorService = ExecutorServiceFactory.createDaemonThreadPoolExecutor("test", concurrency);
        for (int i = 0; i < concurrency; i++) {
            executorService.execute(() -> {
                while (true) {
                    try {
                        slack.scimAsync(orgAdminToken).searchUsers(r -> r.count(1)).get();
                    } catch (Exception e) {
                        logger.info(e.getMessage(), e);
                    }
                }
            });
        }
        Thread.sleep(300_000L);
    }

}
