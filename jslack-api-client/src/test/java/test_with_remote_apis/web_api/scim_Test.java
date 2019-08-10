package test_with_remote_apis.web_api;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.scim.model.Group;
import com.github.seratch.jslack.api.scim.model.User;
import com.github.seratch.jslack.api.scim.request.GroupsPatchRequest;
import com.github.seratch.jslack.api.scim.response.*;
import config.Constants;
import config.SlackTestConfig;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class scim_Test {

    Slack slack = Slack.getInstance(SlackTestConfig.get());
    String token = System.getenv(Constants.SLACK_TEST_SCIM_OAUTH_ACCESS_TOKEN);

    @Test
    public void getServiceProviderConfigs() throws IOException, SlackApiException {
        if (token != null) {
            ServiceProviderConfigsGetResponse response = slack.scim(token).getServiceProviderConfigs(req -> req);
            assertThat(response.getAuthenticationSchemes(), is(notNullValue()));
        }
    }

    @Test
    public void searchAndReadUser() throws IOException, SlackApiException {
        if (token != null) {
            UsersSearchResponse users = slack.scim(token).searchUsers(req -> req.count(1000));
            final String userId = users.getResources().get(0).getId();
            UsersReadResponse read = slack.scim(token).readUser(req -> req.id(userId));
            assertThat(read.getId(), is(userId));
        }
    }

    @Test
    public void createAndDeleteUser() throws Exception {
        if (token != null) {
            User newUser = new User();
            newUser.setName(new User.Name());
            newUser.getName().setGivenName("Kazuhiro");
            newUser.getName().setFamilyName("Sera");
            newUser.setUserName("user" + System.currentTimeMillis());
            User.Email email = new User.Email();
            email.setValue("seratch+" + System.currentTimeMillis() + "@gmail.com");
            newUser.setEmails(Arrays.asList(email));
            UsersCreateResponse creation = slack.scim(token).createUser(req -> req.user(newUser));
            assertThat(creation.getId(), is(notNullValue()));

            User user = new User();
            user.setUserName(creation.getUserName() + "_ed");
            slack.scim(token).patchUser(req -> req.id(creation.getId()).user(user));

            creation.setUserName(creation.getUserName() + "_rv");
            slack.scim(token).updateUser(req -> req.id(creation.getId()).user(creation));

            UsersDeleteResponse deletion = slack.scim(token).deleteUser(req -> req.id(creation.getId()));
            assertThat(deletion, is(nullValue()));
        }
    }

    @Test
    public void groups() throws IOException, SlackApiException {
        if (token != null) {
            GroupsSearchResponse groups = slack.scim(token).searchGroups(req -> req.count(1000));
            if (groups.getResources().size() == 0) {
                Group newGroup = new Group();
                newGroup.setDisplayName("Test Group" + System.currentTimeMillis());
                slack.scim(token).createGroup(req -> req.group(newGroup));
            }

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

}
