package test_with_remote_apis.scim;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.scim.SCIMApiException;
import com.github.seratch.jslack.api.scim.model.Group;
import com.github.seratch.jslack.api.scim.model.User;
import com.github.seratch.jslack.api.scim.request.GroupsPatchRequest;
import com.github.seratch.jslack.api.scim.response.*;
import config.Constants;
import config.SlackTestConfig;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

// required scope - admin
public class ApiTest {

    Slack slack = Slack.getInstance(SlackTestConfig.get());
    String token = System.getenv(Constants.SLACK_TEST_ADMIN_OAUTH_ACCESS_TOKEN);

    @Test
    public void getServiceProviderConfigs() throws IOException, SCIMApiException {
        if (token != null) {
            ServiceProviderConfigsGetResponse response = slack.scim(token).getServiceProviderConfigs(req -> req);
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
        if (token != null) {
            {
                UsersSearchResponse users = slack.scim(token).searchUsers(req -> req.count(1000));
                final String userId = users.getResources().get(0).getId();
                UsersReadResponse read = slack.scim(token).readUser(req -> req.id(userId));
                assertThat(read.getId(), is(userId));
            }
            {
                // pagination
                UsersSearchResponse users = slack.scim(token).searchUsers(req -> req.count(1).startIndex(2));
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
            assertThat(e.getError(), is(notNullValue()));
            assertThat(e.getError().getErrors().getCode(), is(401));
            assertThat(e.getError().getErrors().getDescription(), is("invalid_authentication"));
        }
    }

    @Test
    public void createAndDeleteUser() throws Exception {
        if (token != null) {
            String userName = "user" + System.currentTimeMillis();
            User newUser = new User();
            newUser.setName(new User.Name());
            newUser.getName().setGivenName("Kazuhiro");
            newUser.getName().setFamilyName("Sera");
            newUser.setUserName(userName);
            User.Email email = new User.Email();
            email.setValue("seratch+" + System.currentTimeMillis() + "@gmail.com");
            newUser.setEmails(Arrays.asList(email));
            UsersCreateResponse creation = slack.scim(token).createUser(req -> req.user(newUser));
            assertThat(creation.getId(), is(notNullValue()));

            UsersReadResponse readResp = slack.scim(token).readUser(req -> req.id(creation.getId()));
            assertThat(readResp.getId(), is(creation.getId()));
            assertThat(readResp.getUserName(), is(creation.getUserName()));

            try {
                // https://api.slack.com/scim#filter
                // filter query matching the created data
                UsersSearchResponse searchResp = slack.scim(token).searchUsers(req -> req.count(1).filter("userName eq \"" + userName + "\""));
                assertThat(searchResp.getItemsPerPage(), is(1));
                assertThat(searchResp.getResources().size(), is(1));
                assertThat(searchResp.getResources().get(0).getId(), is(creation.getId()));
                assertThat(searchResp.getResources().get(0).getUserName(), is(creation.getUserName()));

                String originalUserName = creation.getUserName();

                User user = new User();
                user.setUserName(originalUserName + "_ed");
                slack.scim(token).patchUser(req -> req.id(creation.getId()).user(user));

                readResp = slack.scim(token).readUser(req -> req.id(creation.getId()));
                assertThat(readResp.getId(), is(creation.getId()));
                assertThat(readResp.getUserName(), is(originalUserName + "_ed"));

                User modifiedUser = readResp;
                modifiedUser.setUserName(originalUserName + "_rv");
                modifiedUser.setNickName(originalUserName + "_rv"); // required
                slack.scim(token).updateUser(req -> req.id(modifiedUser.getId()).user(modifiedUser));

                readResp = slack.scim(token).readUser(req -> req.id(modifiedUser.getId()));
                assertThat(readResp.getId(), is(creation.getId()));
                assertThat(readResp.getUserName(), is(originalUserName + "_rv"));

            } finally {
                UsersDeleteResponse deletion = slack.scim(token).deleteUser(req -> req.id(creation.getId()));
                assertThat(deletion, is(nullValue()));
                // can call twice
                UsersDeleteResponse deletion2 = slack.scim(token).deleteUser(req -> req.id(creation.getId()));
                assertThat(deletion2, is(nullValue()));

                int counter = 0;
                UsersReadResponse supposedToBeDeleted = null;
                while (counter < 10) {
                    Thread.sleep(1000);
                    supposedToBeDeleted = slack.scim(token).readUser(req -> req.id(creation.getId()));
                    if (!supposedToBeDeleted.getActive()) {
                        break;
                    }
                }
                assertThat(supposedToBeDeleted, is(notNullValue()));
                assertThat(supposedToBeDeleted.getActive(), is(false));

                // can call again
                UsersDeleteResponse deletion3 = slack.scim(token).deleteUser(req -> req.id(creation.getId()));
                assertThat(deletion3, is(nullValue()));
            }
        }
    }

    @Test
    public void groups() throws IOException, SCIMApiException {
        if (token != null) {
            GroupsSearchResponse groups = slack.scim(token).searchGroups(req -> req.count(1000));
            if (groups.getResources().size() == 0) {
                Group newGroup = new Group();
                newGroup.setDisplayName("Test Group" + System.currentTimeMillis());
                slack.scim(token).createGroup(req -> req.group(newGroup));
            }

            // pagination
            GroupsSearchResponse pagination = slack.scim(token).searchGroups(req -> req.count(1));
            assertThat(pagination.getResources().size(), is(1));

            Group group = groups.getResources().get(0);

            GroupsPatchRequest.GroupOperation op = new GroupsPatchRequest.GroupOperation();
            GroupsPatchRequest.MemberOperation memberOp = new GroupsPatchRequest.MemberOperation();
            User user = slack.scim(token).searchUsers(req -> req.count(1)).getResources().get(0);
            memberOp.setValue(user.getId());
            memberOp.setOperation("delete");
            op.setMembers(Arrays.asList(memberOp));

            slack.scim(token).patchGroup(req -> req.id(group.getId()).group(op));

            slack.scim(token).updateGroup(req -> req.id(group.getId()).group(group));

            GroupsReadResponse read = slack.scim(token).readGroup(req -> req.id(group.getId()));
            assertThat(read.getId(), is(group.getId()));
        }
    }

    @Test
    public void groups_dummy() throws IOException {
        try {
            slack.scim("dummy").searchGroups(req -> req.count(1000));
        } catch (SCIMApiException e) {
            assertThat(e.getError(), is(notNullValue()));
            assertThat(e.getError().getErrors().getCode(), is(401));
            assertThat(e.getError().getErrors().getDescription(), is("invalid_authentication"));
        }
    }

}
