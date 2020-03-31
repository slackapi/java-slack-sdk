package test_with_remote_apis.scim;

import com.slack.api.Slack;
import com.slack.api.scim.SCIMApiException;
import com.slack.api.scim.model.Group;
import com.slack.api.scim.model.User;
import com.slack.api.scim.request.GroupsPatchRequest;
import com.slack.api.scim.response.*;
import config.Constants;
import config.SlackTestConfig;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

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

    private UsersCreateResponse createNewUser() throws IOException, SCIMApiException {
        String userName = "user" + System.currentTimeMillis();
        User newUser = new User();
        newUser.setName(new User.Name());
        newUser.getName().setGivenName("Kazuhiro");
        newUser.getName().setFamilyName("Sera");
        newUser.setUserName(userName);
        User.Email email = new User.Email();
        email.setValue("seratch+" + System.currentTimeMillis() + "@gmail.com");
        newUser.setEmails(Arrays.asList(email));
        return slack.scim(orgAdminToken).createUser(req -> req.user(newUser));
    }

    @Test
    public void getServiceProviderConfigs() throws IOException, SCIMApiException {
        if (orgAdminToken != null) {
            ServiceProviderConfigsGetResponse response = slack.scim(orgAdminToken).getServiceProviderConfigs(req -> req);
            assertThat(response.getAuthenticationSchemes(), is(notNullValue()));
        }
    }

    @Test
    public void getServiceProviderConfigs_dummy() throws IOException, SCIMApiException {
        ServiceProviderConfigsGetResponse response = slack.scim("dummy").getServiceProviderConfigs(req -> req);
        assertThat(response.getAuthenticationSchemes(), is(notNullValue()));
    }

    @Test
    public void searchAndReadUser() throws IOException, SCIMApiException {
        if (orgAdminToken != null) {
            {
                UsersSearchResponse users = slack.scim(orgAdminToken).searchUsers(req -> req.count(1000));
                final String userId = users.getResources().get(0).getId();
                UsersReadResponse read = slack.scim(orgAdminToken).readUser(req -> req.id(userId));
                assertThat(read.getId(), is(userId));
            }
            {
                // pagination
                UsersSearchResponse users = slack.scim(orgAdminToken).searchUsers(req -> req.count(1).startIndex(2));
                assertThat(users.getItemsPerPage(), is(1));
                assertThat(users.getResources().size(), is(1));
                assertThat(users.getStartIndex(), is(2));
            }
        }
    }

    @Test
    public void searchUser_dummy() throws IOException {
        try {
            slack.scim("dummy").searchUsers(req -> req.count(1000));
        } catch (SCIMApiException e) {
            assertThat(e.getMessage(), is("status: 401, description: invalid_authentication"));
            assertThat(e.getError(), is(notNullValue()));
            assertThat(e.getError().getErrors().getCode(), is(401));
            assertThat(e.getError().getErrors().getDescription(), is("invalid_authentication"));
        }
    }

    @Test
    public void readUser_dummy() throws IOException {
        try {
            slack.scim("dummy").readUser(req -> req.id("U12345678"));
        } catch (SCIMApiException e) {
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

            UsersReadResponse readResp = slack.scim(orgAdminToken).readUser(req -> req.id(creation.getId()));
            assertThat(readResp.getId(), is(creation.getId()));
            assertThat(readResp.getUserName(), is(creation.getUserName()));

            try {
                // https://api.slack.com/scim#filter
                // filter query matching the created data
                String userName = creation.getUserName();
                UsersSearchResponse searchResp = slack.scim(orgAdminToken).searchUsers(req -> req.count(1).filter("userName eq \"" + userName + "\""));
                assertThat(searchResp.getItemsPerPage(), is(1));
                assertThat(searchResp.getResources().size(), is(1));
                assertThat(searchResp.getResources().get(0).getId(), is(creation.getId()));
                assertThat(searchResp.getResources().get(0).getUserName(), is(creation.getUserName()));

                String originalUserName = creation.getUserName();

                User user = new User();
                user.setUserName(originalUserName + "_ed");
                slack.scim(orgAdminToken).patchUser(req -> req.id(creation.getId()).user(user));

                readResp = slack.scim(orgAdminToken).readUser(req -> req.id(creation.getId()));
                assertThat(readResp.getId(), is(creation.getId()));
                assertThat(readResp.getUserName(), is(originalUserName + "_ed"));

                User modifiedUser = readResp;
                modifiedUser.setUserName(originalUserName + "_rv");
                modifiedUser.setNickName(originalUserName + "_rv"); // required
                slack.scim(orgAdminToken).updateUser(req -> req.id(modifiedUser.getId()).user(modifiedUser));

                readResp = slack.scim(orgAdminToken).readUser(req -> req.id(modifiedUser.getId()));
                assertThat(readResp.getId(), is(creation.getId()));
                assertThat(readResp.getUserName(), is(originalUserName + "_rv"));

            } finally {
                UsersDeleteResponse deletion = slack.scim(orgAdminToken).deleteUser(req -> req.id(creation.getId()));
                assertThat(deletion, is(nullValue()));
                // can call twice
                UsersDeleteResponse deletion2 = slack.scim(orgAdminToken).deleteUser(req -> req.id(creation.getId()));
                assertThat(deletion2, is(nullValue()));

                int counter = 0;
                UsersReadResponse supposedToBeDeleted = null;
                while (counter < 10) {
                    Thread.sleep(1000);
                    supposedToBeDeleted = slack.scim(orgAdminToken).readUser(req -> req.id(creation.getId()));
                    if (!supposedToBeDeleted.getActive()) {
                        break;
                    }
                }
                assertThat(supposedToBeDeleted, is(notNullValue()));
                assertThat(supposedToBeDeleted.getActive(), is(false));

                // can call again
                UsersDeleteResponse deletion3 = slack.scim(orgAdminToken).deleteUser(req -> req.id(creation.getId()));
                assertThat(deletion3, is(nullValue()));
            }
        }
    }

    @Test
    public void groups() throws IOException, SCIMApiException {
        if (orgAdminToken != null) {
            GroupsSearchResponse groups = slack.scim(orgAdminToken).searchGroups(req -> req.count(1000));
            if (groups.getResources().size() == 0) {
                Group newGroup = new Group();
                newGroup.setDisplayName("Test Group" + System.currentTimeMillis());

                Group.Member member = new Group.Member();
                UsersCreateResponse newUser = createNewUser();
                assertThat(newUser.getId(), is(notNullValue()));
                member.setValue(newUser.getId());
                newGroup.setMembers(Arrays.asList(member));

                GroupsCreateResponse createdGroup = slack.scim(orgAdminToken).createGroup(req -> req.group(newGroup));
                assertThat(createdGroup.getMembers().size(), is(1));
            }

            // pagination
            GroupsSearchResponse pagination = slack.scim(orgAdminToken).searchGroups(req -> req.count(1));
            assertThat(pagination.getResources().size(), is(1));

            Group group = groups.getResources().get(0);

            GroupsPatchRequest.GroupOperation op = new GroupsPatchRequest.GroupOperation();
            GroupsPatchRequest.MemberOperation memberOp = new GroupsPatchRequest.MemberOperation();
            User user = slack.scim(orgAdminToken).searchUsers(req -> req.count(1)).getResources().get(0);
            memberOp.setValue(user.getId());
            memberOp.setOperation("delete");
            op.setMembers(Arrays.asList(memberOp));

            slack.scim(orgAdminToken).patchGroup(req -> req.id(group.getId()).group(op));

            slack.scim(orgAdminToken).updateGroup(req -> req.id(group.getId()).group(group));

            GroupsReadResponse read = slack.scim(orgAdminToken).readGroup(req -> req.id(group.getId()));
            assertThat(read.getId(), is(group.getId()));
        }
    }

    @Test
    public void groupAndUsers() throws IOException, SCIMApiException {
        if (orgAdminToken != null) {
            Group newGroup = new Group();
            newGroup.setDisplayName("Test Group" + System.currentTimeMillis());
            UsersCreateResponse newUser = createNewUser();
            assertThat(newUser.getId(), is(notNullValue()));
            try {
                Group.Member member = new Group.Member();
                member.setValue(newUser.getId());
                newGroup.setMembers(Arrays.asList(member));
                GroupsCreateResponse createdGroup = slack.scim(orgAdminToken).createGroup(req -> req.group(newGroup));
                assertThat(createdGroup.getMembers().size(), is(1));
                try {
                    String userName = newUser.getUserName();
                    UsersSearchResponse searchResp = slack.scim(orgAdminToken).searchUsers(req -> req.count(1).filter("userName eq \"" + userName + "\""));
                    assertThat(searchResp.getResources().size(), is(1));
                    assertThat(searchResp.getResources().get(0).getGroups().size(), is(1));
                } finally {
                    GroupsDeleteResponse groupDeletion = slack.scim(orgAdminToken).deleteGroup(req -> req.id(createdGroup.getId()));
                    assertThat(groupDeletion, is(nullValue()));
                }
            } finally {
                UsersDeleteResponse deletion = slack.scim(orgAdminToken).deleteUser(req -> req.id(newUser.getId()));
                assertThat(deletion, is(nullValue()));
            }
        }
    }

    @Test
    public void groups_dummy() throws IOException {
        try {
            slack.scim("dummy").searchGroups(req -> req.count(1000));
        } catch (SCIMApiException e) {
            assertThat(e.getMessage(), is("status: 401, description: invalid_authentication"));
            assertThat(e.getError(), is(notNullValue()));
            assertThat(e.getError().getErrors().getCode(), is(401));
            assertThat(e.getError().getErrors().getDescription(), is("invalid_authentication"));
        }
    }

}
