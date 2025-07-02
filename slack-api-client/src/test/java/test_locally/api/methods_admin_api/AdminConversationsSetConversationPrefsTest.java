package test_locally.api.methods_admin_api;

import com.slack.api.methods.request.admin.conversations.AdminConversationsSetConversationPrefsRequest;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

// see https://docs.slack.dev/reference/methods/admin.conversations.setConversationPrefs
public class AdminConversationsSetConversationPrefsTest {

    @Test
    public void pref() {
        AdminConversationsSetConversationPrefsRequest.Pref pref =
                new AdminConversationsSetConversationPrefsRequest.Pref();
        pref.setTypes(Arrays.asList("admin"));
        pref.setUsers(Arrays.asList("W111"));
        pref.setSubteams(Arrays.asList("S111"));
        assertThat(pref.toValue(), is("type:admin,user:W111,subteam:S111"));
    }
}
