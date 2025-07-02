package test_with_remote_apis.scim2;

import com.slack.api.Slack;
import com.slack.api.scim2.SCIM2ApiException;
import com.slack.api.scim2.model.Group;
import com.slack.api.scim2.model.PatchOperation;
import com.slack.api.scim2.model.User;
import com.slack.api.scim2.request.GroupsPatchOperation;
import com.slack.api.scim2.request.UsersPatchOperation;
import com.slack.api.scim2.response.*;
import config.Constants;
import config.SlackTestConfig;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

// required scope - admin
public class ApiTest {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    String orgAdminToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN);

    private UsersCreateResponse createNewFullUser() throws IOException, SCIM2ApiException {
        return createNewUser(true);
    }

    private UsersCreateResponse createNewGuestUser() throws IOException, SCIM2ApiException {
        return createNewUser(false);
    }

    private UsersCreateResponse createNewUser(boolean isFullMember) throws IOException, SCIM2ApiException {
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
        return slack.scim2(orgAdminToken).createUser(req -> req.user(newUser));
    }

    @Test
    public void getServiceProviderConfigs() throws IOException, SCIM2ApiException {
        if (orgAdminToken != null) {
            ServiceProviderConfigsGetResponse response = slack.scim2(orgAdminToken).getServiceProviderConfigs(req -> req);
            assertThat(response.getAuthenticationSchemes(), is(notNullValue()));
        }
    }

    @Test
    public void getServiceProviderConfigs_dummy() throws IOException, SCIM2ApiException {
        ServiceProviderConfigsGetResponse response = slack.scim2("dummy").getServiceProviderConfigs(req -> req);
        assertThat(response.getAuthenticationSchemes(), is(notNullValue()));
    }

    @Test
    public void getResourceTypes() throws IOException, SCIM2ApiException {
        if (orgAdminToken != null) {
            ResourceTypesGetResponse response = slack.scim2(orgAdminToken).getResourceTypes(req -> req);
        }
    }

    @Test
    public void searchAndReadUser() throws IOException, SCIM2ApiException {
        if (orgAdminToken != null) {
            {
                UsersSearchResponse users = slack.scim2(orgAdminToken).searchUsers(req -> req.count(1000));
                final String userId = users.getResources().get(0).getId();
                UsersReadResponse read = slack.scim2(orgAdminToken).readUser(req -> req.id(userId));
                assertThat(read.getId(), is(userId));
            }
            {
                // pagination
                UsersSearchResponse users = slack.scim2(orgAdminToken).searchUsers(req -> req.count(1).startIndex(2));
                assertThat(users.getItemsPerPage(), is(1));
                assertThat(users.getResources().size(), is(1));
                assertThat(users.getStartIndex(), is(2));
            }
        }
    }

    @Test
    public void searchUser_dummy() throws IOException {
        try {
            slack.scim2("dummy").searchUsers(req -> req.count(1000));
        } catch (SCIM2ApiException e) {
            assertThat(e.getMessage(), is("status: 400, description: unsupported_version"));
            assertThat(e.getError(), is(notNullValue()));
            assertThat(e.getError().getErrors().getCode(), is(400));
            assertThat(e.getError().getErrors().getDescription(), is("unsupported_version"));
        }
    }

    @Test
    public void readUser_dummy() throws IOException {
        try {
            slack.scim2("dummy").readUser(req -> req.id("U12345678"));
        } catch (SCIM2ApiException e) {
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

            UsersReadResponse readResp = slack.scim2(orgAdminToken).readUser(req -> req.id(creation.getId()));
            assertThat(readResp.getId(), is(creation.getId()));
            assertThat(readResp.getUserName(), is(creation.getUserName()));

            try {
                // https://docs.slack.dev/reference/scim-api#filter
                // filter query matching the created data
                String userName = creation.getUserName();
                UsersSearchResponse searchResp = slack.scim2(orgAdminToken).searchUsers(req -> req.count(1).filter("userName eq \"" + userName + "\""));
                assertThat(searchResp.getItemsPerPage(), is(1));
                assertThat(searchResp.getResources().size(), is(1));
                assertThat(searchResp.getResources().get(0).getId(), is(creation.getId()));
                assertThat(searchResp.getResources().get(0).getUserName(), is(creation.getUserName()));

                String originalUserName = creation.getUserName();

                slack.scim2(orgAdminToken).patchUser(req -> req.id(creation.getId()).operations(Arrays.asList(
                        UsersPatchOperation.builder()
                                .op(PatchOperation.Replace)
                                .path("userName")
                                .stringValue(originalUserName + "_ed")
                                .build()
                )));

                readResp = slack.scim2(orgAdminToken).readUser(req -> req.id(creation.getId()));
                assertThat(readResp.getId(), is(creation.getId()));
                assertThat(readResp.getUserName(), is(originalUserName + "_ed"));

                User modifiedUser = readResp;
                modifiedUser.setUserName(originalUserName + "_rv");
                modifiedUser.setNickName(originalUserName + "_rv"); // required
                slack.scim2(orgAdminToken).updateUser(req -> req.id(modifiedUser.getId()).user(modifiedUser));

                readResp = slack.scim2(orgAdminToken).readUser(req -> req.id(modifiedUser.getId()));
                assertThat(readResp.getId(), is(creation.getId()));
                assertThat(readResp.getUserName(), is(originalUserName + "_rv"));

            } finally {
                UsersDeleteResponse deletion = slack.scim2(orgAdminToken).deleteUser(req -> req.id(creation.getId()));
                assertThat(deletion, is(nullValue()));
                // can call twice
                UsersDeleteResponse deletion2 = slack.scim2(orgAdminToken).deleteUser(req -> req.id(creation.getId()));
                assertThat(deletion2, is(nullValue()));

                int counter = 0;
                UsersReadResponse supposedToBeDeleted = null;
                while (counter < 10) {
                    Thread.sleep(1000);
                    supposedToBeDeleted = slack.scim2(orgAdminToken).readUser(req -> req.id(creation.getId()));
                    if (!supposedToBeDeleted.getActive()) {
                        break;
                    }
                }
                assertThat(supposedToBeDeleted, is(notNullValue()));
                assertThat(supposedToBeDeleted.getActive(), is(false));

                // can call again
                UsersDeleteResponse deletion3 = slack.scim2(orgAdminToken).deleteUser(req -> req.id(creation.getId()));
                assertThat(deletion3, is(nullValue()));
            }
        }
    }

    @Test
    public void createAndDeleteGuestUser() throws Exception {
        if (orgAdminToken != null) {
            UsersCreateResponse creation = createNewGuestUser();
            assertThat(creation.getId(), is(notNullValue()));

            UsersReadResponse readResp = slack.scim2(orgAdminToken).readUser(req -> req.id(creation.getId()));
            assertThat(readResp.getId(), is(creation.getId()));
            assertThat(readResp.getUserName(), is(creation.getUserName()));
            assertThat(readResp.getSlackGuest().getType(), is(User.SlackGuest.Types.MULTI));
            assertThat(readResp.getSlackGuest().getExpiration(), is(notNullValue()));

            try {
                // https://docs.slack.dev/reference/scim-api#filter
                // filter query matching the created data
                String userName = creation.getUserName();
                UsersSearchResponse searchResp = slack.scim2(orgAdminToken).searchUsers(req -> req.count(1).filter("userName eq \"" + userName + "\""));
                assertThat(searchResp.getItemsPerPage(), is(1));
                assertThat(searchResp.getResources().size(), is(1));
                assertThat(searchResp.getResources().get(0).getId(), is(creation.getId()));
                assertThat(searchResp.getResources().get(0).getUserName(), is(creation.getUserName()));
                assertThat(searchResp.getResources().get(0).getSlackGuest(), is(notNullValue()));

                String originalUserName = creation.getUserName();
                slack.scim2(orgAdminToken).patchUser(req -> req.id(creation.getId()).operations(Arrays.asList(
                        UsersPatchOperation.builder()
                                .op(PatchOperation.Replace)
                                .path("userName")
                                .stringValue(originalUserName + "_ed")
                                .build()
                )));

                readResp = slack.scim2(orgAdminToken).readUser(req -> req.id(creation.getId()));
                assertThat(readResp.getId(), is(creation.getId()));
                assertThat(readResp.getUserName(), is(originalUserName + "_ed"));

                User modifiedUser = readResp;
                modifiedUser.setUserName(originalUserName + "_rv");
                modifiedUser.setNickName(originalUserName + "_rv"); // required
                slack.scim2(orgAdminToken).updateUser(req -> req.id(modifiedUser.getId()).user(modifiedUser));

                readResp = slack.scim2(orgAdminToken).readUser(req -> req.id(modifiedUser.getId()));
                assertThat(readResp.getId(), is(creation.getId()));
                assertThat(readResp.getUserName(), is(originalUserName + "_rv"));
                assertThat(readResp.getSlackGuest().getType(), is(User.SlackGuest.Types.MULTI));
                assertThat(readResp.getSlackGuest().getExpiration(), is(notNullValue()));

            } finally {
                UsersDeleteResponse deletion = slack.scim2(orgAdminToken).deleteUser(req -> req.id(creation.getId()));
                assertThat(deletion, is(nullValue()));

                // can call twice
                UsersDeleteResponse deletion2 = slack.scim2(orgAdminToken).deleteUser(req -> req.id(creation.getId()));
                assertThat(deletion2, is(nullValue()));

                // Verify if the deletion is completed (there is some delay)
                int counter = 0;
                UsersReadResponse supposedToBeDeleted = null;
                while (counter < 10) {
                    Thread.sleep(1000);
                    supposedToBeDeleted = slack.scim2(orgAdminToken).readUser(req -> req.id(creation.getId()));
                    if (!supposedToBeDeleted.getActive()) {
                        break;
                    }
                }
                assertThat(supposedToBeDeleted, is(notNullValue()));
                assertThat(supposedToBeDeleted.getActive(), is(false));

                // can call again
                UsersDeleteResponse deletion3 = slack.scim2(orgAdminToken).deleteUser(req -> req.id(creation.getId()));
                assertThat(deletion3, is(nullValue()));
            }
        }
    }

    @Test
    public void groups() throws IOException, SCIM2ApiException {
        if (orgAdminToken != null) {
            GroupsSearchResponse groups = slack.scim2(orgAdminToken).searchGroups(req -> req.count(1000));
            if (groups.getResources().size() == 0) {
                Group newGroup = new Group();
                newGroup.setDisplayName("Test Group" + System.currentTimeMillis());

                Group.Member member = new Group.Member();
                UsersCreateResponse newUser = createNewFullUser();
                assertThat(newUser.getId(), is(notNullValue()));
                member.setValue(newUser.getId());
                newGroup.setMembers(Arrays.asList(member));

                GroupsCreateResponse createdGroup = slack.scim2(orgAdminToken).createGroup(req -> req.group(newGroup));
                assertThat(createdGroup.getMembers().size(), is(1));
            }

            // pagination
            GroupsSearchResponse pagination = slack.scim2(orgAdminToken).searchGroups(req -> req.count(1));
            assertThat(pagination.getResources().size(), is(1));

            Group group = groups.getResources().get(0);

            User user = slack.scim2(orgAdminToken).searchUsers(req -> req.count(1)).getResources().get(0);
            slack.scim2(orgAdminToken).patchGroup(req -> req.id(group.getId()).operations(Arrays.asList(
                    GroupsPatchOperation.builder()
                            .path("members")
                            .op(PatchOperation.Remove)
                            .memberValues(Arrays.asList(GroupsPatchOperation.Member.builder().value(user.getId()).build()))
                            .build()
            )));

            slack.scim2(orgAdminToken).updateGroup(req -> req.id(group.getId()).group(group));

            GroupsReadResponse read = slack.scim2(orgAdminToken).readGroup(req -> req.id(group.getId()));
            assertThat(read.getId(), is(group.getId()));
        }
    }

    @Test
    public void groupAndUsers() throws IOException, SCIM2ApiException {
        if (orgAdminToken != null) {
            Group newGroup = new Group();
            newGroup.setDisplayName("Test Group" + System.currentTimeMillis());
            UsersCreateResponse newUser = createNewFullUser();
            assertThat(newUser.getId(), is(notNullValue()));
            try {
                Group.Member member = new Group.Member();
                member.setValue(newUser.getId());
                newGroup.setMembers(Arrays.asList(member));
                GroupsCreateResponse createdGroup = slack.scim2(orgAdminToken).createGroup(req -> req.group(newGroup));
                assertThat(createdGroup.getMembers().size(), is(1));
                try {
                    String userName = newUser.getUserName();
                    UsersSearchResponse searchResp = slack.scim2(orgAdminToken).searchUsers(req -> req.count(1).filter("userName eq \"" + userName + "\""));
                    assertThat(searchResp.getResources().size(), is(1));
                    assertThat(searchResp.getResources().get(0).getGroups().size(), is(1));
                } finally {
                    GroupsDeleteResponse groupDeletion = slack.scim2(orgAdminToken).deleteGroup(req -> req.id(createdGroup.getId()));
                    assertThat(groupDeletion, is(nullValue()));
                }
            } finally {
                UsersDeleteResponse deletion = slack.scim2(orgAdminToken).deleteUser(req -> req.id(newUser.getId()));
                assertThat(deletion, is(nullValue()));
            }
        }
    }

    @Test
    public void groups_dummy() throws IOException {
        try {
            slack.scim2("dummy").searchGroups(req -> req.count(1000));
        } catch (SCIM2ApiException e) {
            assertThat(e.getMessage(), is("status: 400, description: unsupported_version"));
            assertThat(e.getError(), is(notNullValue()));
            assertThat(e.getError().getErrors().getCode(), is(400));
            assertThat(e.getError().getErrors().getDescription(), is("unsupported_version"));
        }
    }

}
