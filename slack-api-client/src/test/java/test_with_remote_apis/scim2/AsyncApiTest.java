package test_with_remote_apis.scim2;

import com.slack.api.Slack;
import com.slack.api.scim2.SCIM2ApiCompletionException;
import com.slack.api.scim2.SCIM2ApiException;
import com.slack.api.scim2.model.Group;
import com.slack.api.scim2.model.PatchOperation;
import com.slack.api.scim2.model.User;
import com.slack.api.scim2.request.GroupsPatchOperation;
import com.slack.api.scim2.request.UsersPatchOperation;
import com.slack.api.scim2.response.*;
import com.slack.api.util.thread.DaemonThreadExecutorServiceProvider;
import config.Constants;
import config.SlackTestConfig;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
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

    private UsersCreateResponse createNewFullUser() throws Exception {
        return createNewUser(true);
    }

    private UsersCreateResponse createNewGuestUser() throws Exception {
        return createNewUser(false);
    }

    private UsersCreateResponse createNewUser(boolean isFullMember) throws Exception {
        String userName = "user" + System.currentTimeMillis();
        User newUser = new User();
        newUser.setName(new User.Name());
        newUser.getName().setGivenName("Kazuhiro");
        newUser.getName().setFamilyName("Sera");
        newUser.setUserName(userName);
        User.Email email = new User.Email();
        email.setValue("seratch+" + System.currentTimeMillis() + "@gmail.com");
        newUser.setEmails(Arrays.asList(email));
        if (!isFullMember) {
            // guest
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
            String expiration = df.format(Date.from(ZonedDateTime.now().plusDays(14).toInstant()));
            newUser.setSlackGuest(User.SlackGuest.builder()
                    .type(User.SlackGuest.Types.MULTI)
                    .expiration(expiration)
                    .build());
        }
        return slack.scim2Async(orgAdminToken).createUser(req -> req.user(newUser)).get();
    }

    @Test
    public void getServiceProviderConfigs() throws ExecutionException, InterruptedException {
        if (orgAdminToken != null) {
            ServiceProviderConfigsGetResponse response = slack.scim2Async(orgAdminToken)
                    .getServiceProviderConfigs(req -> req).get();
            assertThat(response.getAuthenticationSchemes(), is(notNullValue()));
        }
    }

    @Test
    public void getServiceProviderConfigs_dummy() throws ExecutionException, InterruptedException {
        ServiceProviderConfigsGetResponse response = slack.scim2Async("dummy")
                .getServiceProviderConfigs(req -> req).get();
        assertThat(response.getAuthenticationSchemes(), is(notNullValue()));
    }

    @Test
    public void searchAndReadUser() throws ExecutionException, InterruptedException {
        if (orgAdminToken != null) {
            {
                UsersSearchResponse users = slack.scim2Async(orgAdminToken).searchUsers(req -> req.count(1000)).get();
                final String userId = users.getResources().get(0).getId();
                UsersReadResponse read = slack.scim2Async(orgAdminToken).readUser(req -> req.id(userId)).get();
                assertThat(read.getId(), is(userId));
            }
            {
                // pagination
                UsersSearchResponse users = slack.scim2Async(orgAdminToken)
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
            slack.scim2Async("dummy").searchUsers(req -> req.count(1000)).get();
        } catch (Exception _e) {
            SCIM2ApiException e = ((SCIM2ApiCompletionException) _e.getCause()).getScimApiException();
            assertThat(e.getMessage(), is("status: 400, description: unsupported_version"));
            assertThat(e.getError(), is(notNullValue()));
            assertThat(e.getError().getErrors().getCode(), is(400));
            assertThat(e.getError().getErrors().getDescription(), is("unsupported_version"));
        }
    }

    @Test
    public void readUser_dummy() {
        try {
            slack.scim2Async("dummy").readUser(req -> req.id("U12345678")).get();
        } catch (Exception _e) {
            SCIM2ApiException e = ((SCIM2ApiCompletionException) _e.getCause()).getScimApiException();
            assertThat(e.getMessage(), is("status: 400, description: unsupported_version"));
            assertThat(e.getError(), is(notNullValue()));
            assertThat(e.getError().getErrors().getCode(), is(400));
            assertThat(e.getError().getErrors().getDescription(), is("unsupported_version"));
        }
    }

    @Test
    public void createAndDeleteFullUser() throws Exception {
        if (orgAdminToken != null) {
            UsersCreateResponse creation = createNewFullUser();
            assertThat(creation.getId(), is(notNullValue()));

            UsersReadResponse readResp = slack.scim2Async(orgAdminToken)
                    .readUser(req -> req.id(creation.getId())).get();
            assertThat(readResp.getId(), is(creation.getId()));
            assertThat(readResp.getUserName(), is(creation.getUserName()));

            try {
                // https://docs.slack.dev/reference/scim-api#filter
                // filter query matching the created data
                String userName = creation.getUserName();
                UsersSearchResponse searchResp = slack.scim2Async(orgAdminToken)
                        .searchUsers(req -> req.count(1).filter("userName eq \"" + userName + "\"")).get();
                assertThat(searchResp.getItemsPerPage(), is(1));
                assertThat(searchResp.getResources().size(), is(1));
                assertThat(searchResp.getResources().get(0).getId(), is(creation.getId()));
                assertThat(searchResp.getResources().get(0).getUserName(), is(creation.getUserName()));

                String originalUserName = creation.getUserName();

                slack.scim2Async(orgAdminToken).patchUser(req -> req.id(creation.getId()).operations(Arrays.asList(
                        UsersPatchOperation.builder()
                                .op(PatchOperation.Replace)
                                .path("userName")
                                .stringValue(originalUserName + "_ed")
                                .build()
                )));

                Thread.sleep(500L);
                readResp = slack.scim2Async(orgAdminToken).readUser(req -> req.id(creation.getId())).get();
                assertThat(readResp.getId(), is(creation.getId()));
                assertThat(readResp.getUserName(), is(originalUserName + "_ed"));

                User modifiedUser = readResp;
                modifiedUser.setUserName(originalUserName + "_rv");
                modifiedUser.setNickName(originalUserName + "_rv"); // required
                slack.scim2Async(orgAdminToken).updateUser(req -> req.id(modifiedUser.getId()).user(modifiedUser));

                Thread.sleep(500L);
                readResp = slack.scim2Async(orgAdminToken).readUser(req -> req.id(modifiedUser.getId())).get();
                assertThat(readResp.getId(), is(creation.getId()));
                assertThat(readResp.getUserName(), is(originalUserName + "_rv"));

            } finally {
                UsersDeleteResponse deletion = slack.scim2Async(orgAdminToken)
                        .deleteUser(req -> req.id(creation.getId())).get();
                assertThat(deletion, is(nullValue()));

                // can call twice
                UsersDeleteResponse deletion2 = slack.scim2Async(orgAdminToken)
                        .deleteUser(req -> req.id(creation.getId())).get();
                assertThat(deletion2, is(nullValue()));

                // Verify if the deletion is completed (there is some delay)
                int counter = 0;
                UsersReadResponse supposedToBeDeleted = null;
                while (counter < 10) {
                    Thread.sleep(1000);
                    supposedToBeDeleted = slack.scim2Async(orgAdminToken)
                            .readUser(req -> req.id(creation.getId())).get();
                    if (!supposedToBeDeleted.getActive()) {
                        break;
                    }
                }
                assertThat(supposedToBeDeleted, is(notNullValue()));
                assertThat(supposedToBeDeleted.getActive(), is(false));

                // can call again
                UsersDeleteResponse deletion3 = slack.scim2Async(orgAdminToken)
                        .deleteUser(req -> req.id(creation.getId())).get();
                assertThat(deletion3, is(nullValue()));
            }
        }
    }

    @Test
    public void createAndDeleteGuestUser() throws Exception {
        if (orgAdminToken != null) {
            UsersCreateResponse creation = createNewGuestUser();
            assertThat(creation.getId(), is(notNullValue()));

            UsersReadResponse readResp = slack.scim2Async(orgAdminToken)
                    .readUser(req -> req.id(creation.getId())).get();
            assertThat(readResp.getId(), is(creation.getId()));
            assertThat(readResp.getUserName(), is(creation.getUserName()));
            assertThat(readResp.getSlackGuest().getType(), is(User.SlackGuest.Types.MULTI));
            assertThat(readResp.getSlackGuest().getExpiration(), is(notNullValue()));

            try {
                // https://docs.slack.dev/reference/scim-api#filter
                // filter query matching the created data
                String userName = creation.getUserName();
                UsersSearchResponse searchResp = slack.scim2Async(orgAdminToken)
                        .searchUsers(req -> req.count(1).filter("userName eq \"" + userName + "\"")).get();
                assertThat(searchResp.getItemsPerPage(), is(1));
                assertThat(searchResp.getResources().size(), is(1));
                assertThat(searchResp.getResources().get(0).getId(), is(creation.getId()));
                assertThat(searchResp.getResources().get(0).getUserName(), is(creation.getUserName()));
                assertThat(searchResp.getResources().get(0).getUserName(), is(creation.getUserName()));
                assertThat(searchResp.getResources().get(0).getSlackGuest(), is(notNullValue()));

                String originalUserName = creation.getUserName();

                slack.scim2Async(orgAdminToken).patchUser(req -> req.id(creation.getId()).operations(Arrays.asList(
                        UsersPatchOperation.builder()
                                .op(PatchOperation.Replace)
                                .path("userName")
                                .stringValue(originalUserName + "_ed")
                                .build()
                )));

                Thread.sleep(500L);
                readResp = slack.scim2Async(orgAdminToken).readUser(req -> req.id(creation.getId())).get();
                assertThat(readResp.getId(), is(creation.getId()));
                assertThat(readResp.getUserName(), is(originalUserName + "_ed"));

                User modifiedUser = readResp;
                modifiedUser.setUserName(originalUserName + "_rv");
                modifiedUser.setNickName(originalUserName + "_rv"); // required
                slack.scim2Async(orgAdminToken).updateUser(req -> req.id(modifiedUser.getId()).user(modifiedUser));

                Thread.sleep(500L);
                readResp = slack.scim2Async(orgAdminToken).readUser(req -> req.id(modifiedUser.getId())).get();
                assertThat(readResp.getId(), is(creation.getId()));
                assertThat(readResp.getUserName(), is(originalUserName + "_rv"));
                assertThat(readResp.getSlackGuest().getType(), is(User.SlackGuest.Types.MULTI));
                assertThat(readResp.getSlackGuest().getExpiration(), is(notNullValue()));

            } finally {
                UsersDeleteResponse deletion = slack.scim2Async(orgAdminToken)
                        .deleteUser(req -> req.id(creation.getId())).get();
                assertThat(deletion, is(nullValue()));
                // can call twice
                UsersDeleteResponse deletion2 = slack.scim2Async(orgAdminToken)
                        .deleteUser(req -> req.id(creation.getId())).get();
                assertThat(deletion2, is(nullValue()));

                int counter = 0;
                UsersReadResponse supposedToBeDeleted = null;
                while (counter < 10) {
                    Thread.sleep(1000);
                    supposedToBeDeleted = slack.scim2Async(orgAdminToken)
                            .readUser(req -> req.id(creation.getId())).get();
                    if (!supposedToBeDeleted.getActive()) {
                        break;
                    }
                }
                assertThat(supposedToBeDeleted, is(notNullValue()));
                assertThat(supposedToBeDeleted.getActive(), is(false));

                // can call again
                UsersDeleteResponse deletion3 = slack.scim2Async(orgAdminToken)
                        .deleteUser(req -> req.id(creation.getId())).get();
                assertThat(deletion3, is(nullValue()));
            }
        }
    }

    @Test
    public void groups() throws Exception {
        if (orgAdminToken != null) {
            GroupsSearchResponse groups = slack.scim2Async(orgAdminToken)
                    .searchGroups(req -> req.count(1000)).get();
            if (groups.getResources().size() == 0) {
                Group newGroup = new Group();
                newGroup.setDisplayName("Test Group" + System.currentTimeMillis());

                Group.Member member = new Group.Member();
                UsersCreateResponse newUser = createNewFullUser();
                assertThat(newUser.getId(), is(notNullValue()));
                member.setValue(newUser.getId());
                newGroup.setMembers(Arrays.asList(member));

                GroupsCreateResponse createdGroup = slack.scim2Async(orgAdminToken)
                        .createGroup(req -> req.group(newGroup)).get();
                assertThat(createdGroup.getMembers().size(), is(1));
            }

            // pagination
            GroupsSearchResponse pagination = slack.scim2Async(orgAdminToken)
                    .searchGroups(req -> req.count(1)).get();
            assertThat(pagination.getResources().size(), is(1));

            Group group = groups.getResources().get(0);
            User user = slack.scim2Async(orgAdminToken).searchUsers(req -> req.count(1)).get().getResources().get(0);
            slack.scim2Async(orgAdminToken).patchGroup(req -> req.id(group.getId()).operations(Arrays.asList(
                    GroupsPatchOperation.builder()
                            .path("members")
                            .op(PatchOperation.Remove)
                            .memberValues(Arrays.asList(GroupsPatchOperation.Member.builder().value(user.getId()).build()))
                            .build()
            ))).get();

            slack.scim2Async(orgAdminToken).updateGroup(req -> req.id(group.getId()).group(group)).get();

            GroupsReadResponse read = slack.scim2Async(orgAdminToken).readGroup(req -> req.id(group.getId())).get();
            assertThat(read.getId(), is(group.getId()));
        }
    }

    @Test
    public void groupAndUsers() throws Exception {
        if (orgAdminToken != null) {
            Group newGroup = new Group();
            newGroup.setDisplayName("Test Group" + System.currentTimeMillis());
            UsersCreateResponse newUser = createNewFullUser();
            assertThat(newUser.getId(), is(notNullValue()));
            try {
                Group.Member member = new Group.Member();
                member.setValue(newUser.getId());
                newGroup.setMembers(Arrays.asList(member));
                GroupsCreateResponse createdGroup = slack.scim2Async(orgAdminToken)
                        .createGroup(req -> req.group(newGroup)).get();
                assertThat(createdGroup.getMembers().size(), is(1));
                try {
                    String userName = newUser.getUserName();
                    UsersSearchResponse searchResp = slack.scim2Async(orgAdminToken)
                            .searchUsers(req -> req.count(1).filter("userName eq \"" + userName + "\"")).get();
                    assertThat(searchResp.getResources().size(), is(1));
                    assertThat(searchResp.getResources().get(0).getGroups().size(), is(1));
                } finally {
                    GroupsDeleteResponse groupDeletion = slack.scim2Async(orgAdminToken)
                            .deleteGroup(req -> req.id(createdGroup.getId())).get();
                    assertThat(groupDeletion, is(nullValue()));
                }
            } finally {
                UsersDeleteResponse deletion = slack.scim2Async(orgAdminToken)
                        .deleteUser(req -> req.id(newUser.getId())).get();
                assertThat(deletion, is(nullValue()));
            }
        }
    }

    @Test
    public void groups_dummy() throws IOException, ExecutionException, InterruptedException {
        try {
            slack.scim2Async("dummy").searchGroups(req -> req.count(1000)).get();
        } catch (Exception _e) {
            SCIM2ApiException e = ((SCIM2ApiCompletionException) _e.getCause()).getScimApiException();
            assertThat(e.getMessage(), is("status: 400, description: unsupported_version"));
            assertThat(e.getError(), is(notNullValue()));
            assertThat(e.getError().getErrors().getCode(), is(400));
            assertThat(e.getError().getErrors().getDescription(), is("unsupported_version"));
        }
    }

    @Ignore
    @Test
    public void demonstrateRateLimitedErrors() throws Exception {
        Logger logger = LoggerFactory.getLogger(AsyncApiTest.class);
        int concurrency = 10;
        ExecutorService executorService = DaemonThreadExecutorServiceProvider.getInstance().createThreadPoolExecutor("test", concurrency);
        for (int i = 0; i < concurrency; i++) {
            executorService.execute(() -> {
                while (true) {
                    try {
                        slack.scim2Async(orgAdminToken).searchUsers(r -> r.count(1)).get();
                    } catch (Exception e) {
                        logger.info(e.getMessage(), e);
                    }
                }
            });
        }
        Thread.sleep(300_000L);
    }

}
