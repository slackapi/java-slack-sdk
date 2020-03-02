package test_locally.docs;

import com.slack.api.Slack;
import com.slack.api.scim.*;
import com.slack.api.scim.model.*;
import com.slack.api.scim.response.*;
import com.slack.api.scim.request.*;

import java.io.IOException;
import java.util.Arrays;

public class SCIMApiTest {

    public static void main(String[] args) throws IOException, SCIMApiException {
        Slack slack = Slack.getInstance();
        String token = System.getenv("SLACK_ADMIN_ACCESS_TOKN"); // `admin` scope required
        SCIMClient scim = slack.scim(token);

        {
            // Search Users
            UsersSearchResponse users = slack.scim(token).searchUsers(req -> req.count(1000));

            // Read a User
            final String userId = users.getResources().get(0).getId();
            UsersReadResponse read = slack.scim(token).readUser(req -> req.id(userId));
        }


        {
            // Pagination for Users
            UsersSearchResponse users = slack.scim(token).searchUsers(req -> req.count(1).startIndex(2));
            users.getItemsPerPage(); // 1
            users.getResources().size(); // 1
            users.getStartIndex(); // 2
        }

        {
            // Create a new User
            User newUser = new User();
            newUser.setName(new User.Name());
            newUser.getName().setGivenName("Kazuhiro");
            newUser.getName().setFamilyName("Sera");
            // set other fields as well...
            UsersCreateResponse creation = slack.scim(token).createUser(req -> req.user(newUser));

            String userName = "Kaz";
            // Run a filter query for user search
            // https://api.slack.com/scim#filter
            UsersSearchResponse searchResp = slack.scim(token).searchUsers(req -> req
                    .count(1)
                    .filter("userName eq \"" + userName + "\"")
            );
        }

        // Create a Group
        Group newGroup = new Group();
        newGroup.setDisplayName("Awesome Group");
        slack.scim(token).createGroup(req -> req.group(newGroup));

        // Search Groups
        GroupsSearchResponse groups = slack.scim(token).searchGroups(req -> req.count(1000));

        // Pagination
        GroupsSearchResponse pagination = slack.scim(token).searchGroups(req -> req.count(1));
        pagination.getResources().size(); // 1
        pagination.getResources().size(); // 1

        Group group = groups.getResources().get(0);

        // Overwrite values for specified attributes.
        GroupsPatchRequest.GroupOperation op = new GroupsPatchRequest.GroupOperation();
        GroupsPatchRequest.MemberOperation memberOp = new GroupsPatchRequest.MemberOperation();
        User user = slack.scim(token).searchUsers(req -> req.count(1)).getResources().get(0);
        memberOp.setValue(user.getId());
        memberOp.setOperation("delete");
        op.setMembers(Arrays.asList(memberOp));
        slack.scim(token).patchGroup(req -> req.id(group.getId()).group(op));

        String groupId = group.getId();
        // Overwrite all values for a Group even if some attributes are not given
        slack.scim(token).updateGroup(req -> req.id(groupId).group(group));
    }

}
