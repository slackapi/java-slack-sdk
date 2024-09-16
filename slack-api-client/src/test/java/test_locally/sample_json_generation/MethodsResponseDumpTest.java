package test_locally.sample_json_generation;

import com.slack.api.methods.response.admin.apps.*;
import com.slack.api.methods.response.asssistant.threads.AssistantThreadsSetStatusResponse;
import com.slack.api.methods.response.asssistant.threads.AssistantThreadsSetSuggestedPromptsResponse;
import com.slack.api.methods.response.asssistant.threads.AssistantThreadsSetTitleResponse;
import com.slack.api.methods.response.dialog.DialogOpenResponse;
import com.slack.api.methods.response.migration.MigrationExchangeResponse;
import com.slack.api.methods.response.users.UsersGetPresenceResponse;
import com.slack.api.methods.response.users.UsersIdentityResponse;
import com.slack.api.methods.response.views.ViewsOpenResponse;
import com.slack.api.methods.response.views.ViewsPublishResponse;
import com.slack.api.methods.response.views.ViewsPushResponse;
import com.slack.api.methods.response.views.ViewsUpdateResponse;
import com.slack.api.model.ErrorResponseMetadata;
import com.slack.api.model.ResponseMetadata;
import com.slack.api.model.admin.AppRequest;
import com.slack.api.model.admin.AppScope;
import com.slack.api.model.admin.ApprovedApp;
import com.slack.api.model.admin.RestrictedApp;
import com.slack.api.model.dialog.DialogResponseMetadata;
import com.slack.api.model.view.View;
import org.junit.Test;
import util.ObjectInitializer;
import util.sample_json_generation.ObjectToJsonDumper;
import util.sample_json_generation.SampleObjects;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MethodsResponseDumpTest {
    // This test class generates sample JSON data for the Web APIs
    // that are not so easy to run in tests.
    // (e.g., the ones requiring trigger_id for running)

    ObjectToJsonDumper dumper = new ObjectToJsonDumper("../json-logs/samples/api");

    static ResponseMetadata responseMetadata = ObjectInitializer.initProperties(new ResponseMetadata());
    static ErrorResponseMetadata errorResponseMetadata = ObjectInitializer.initProperties(new ErrorResponseMetadata());
    static View view = ObjectInitializer.initProperties(new View());
    static {
        responseMetadata.setMessages(Arrays.asList(""));
        responseMetadata.setWarnings(Arrays.asList(""));
        errorResponseMetadata.setMessages(Arrays.asList(""));
        view.setBlocks(SampleObjects.ModalBlocks);
    }

    @Test
    public void views_open() throws Exception {
        ViewsOpenResponse response = new ViewsOpenResponse();
        ObjectInitializer.initProperties(response);
        response.setView(view);
        response.setResponseMetadata(errorResponseMetadata);
        dumper.dump("views.open", response);
    }

    @Test
    public void views_push() throws Exception {
        ViewsPushResponse response = new ViewsPushResponse();
        ObjectInitializer.initProperties(response);
        response.setView(view);
        response.setResponseMetadata(errorResponseMetadata);
        dumper.dump("views.push", response);
    }

    @Test
    public void views_update() throws Exception {
        ViewsUpdateResponse response = new ViewsUpdateResponse();
        ObjectInitializer.initProperties(response);
        response.setView(view);
        response.setResponseMetadata(errorResponseMetadata);
        dumper.dump("views.update", response);
    }

    @Test
    public void views_publish() throws Exception {
        ViewsPublishResponse response = new ViewsPublishResponse();
        ObjectInitializer.initProperties(response);
        response.setView(view);
        response.setResponseMetadata(errorResponseMetadata);
        dumper.dump("views.publish", response);
    }

    @Test
    public void migration_exchange() throws Exception {
        MigrationExchangeResponse response = new MigrationExchangeResponse();
        response.setInvalidUserIds(Arrays.asList(""));
        Map<String, String> userIdMap = new HashMap<>();
        userIdMap.put("0", "0");
        userIdMap.put("1", "1");
        userIdMap.put("2", "2");
        response.setUserIdMap(userIdMap);
        ObjectInitializer.initProperties(response);
        dumper.dump("migration.exchange", response);
    }

    @Test
    public void users_identity() throws Exception {
        UsersIdentityResponse response = new UsersIdentityResponse();
        ObjectInitializer.initProperties(response);
        dumper.dump("users.identity", response);
    }

    @Test
    public void users_getPresence() throws Exception {
        UsersGetPresenceResponse response = new UsersGetPresenceResponse();
        ObjectInitializer.initProperties(response);
        dumper.dump("users.getPresence", response);
    }

    @Test
    public void dialog_open() throws Exception {
        DialogOpenResponse response = new DialogOpenResponse();
        DialogResponseMetadata metadata = new DialogResponseMetadata();
        metadata.setMessages(Arrays.asList(""));
        response.setResponseMetadata(metadata);
        ObjectInitializer.initProperties(response);
        dumper.dump("dialog.open", response);
    }

    @Test
    public void admin_apps() throws Exception {
        {
            AdminAppsRequestsListResponse response = new AdminAppsRequestsListResponse();
            response.setResponseMetadata(responseMetadata);
            response.setAppRequests(Arrays.asList(ObjectInitializer.initProperties(new AppRequest())));
            ObjectInitializer.initProperties(response);
            dumper.dump("admin.apps.requests.list", response);
        }
        {
            AdminAppsApprovedListResponse response = new AdminAppsApprovedListResponse();
            response.setResponseMetadata(responseMetadata);
            ApprovedApp app = ObjectInitializer.initProperties(new ApprovedApp());
            app.setScopes(Arrays.asList(ObjectInitializer.initProperties(new AppScope())));
            response.setApprovedApps(Arrays.asList(app));
            ObjectInitializer.initProperties(response);
            dumper.dump("admin.apps.approved.list", response);
        }
        {
            AdminAppsRestrictedListResponse response = new AdminAppsRestrictedListResponse();
            ObjectInitializer.initProperties(response);
            response.setResponseMetadata(responseMetadata);
            RestrictedApp app = ObjectInitializer.initProperties(new RestrictedApp());
            app.setScopes(Arrays.asList(ObjectInitializer.initProperties(new AppScope())));
            response.setRestrictedApps(Arrays.asList(app));
            ObjectInitializer.initProperties(response);
            dumper.dump("admin.apps.restricted.list", response);
        }
        {
            AdminAppsApproveResponse response = new AdminAppsApproveResponse();
            ObjectInitializer.initProperties(response);
            dumper.dump("admin.apps.approve", response);
        }
        {
            AdminAppsRestrictResponse response = new AdminAppsRestrictResponse();
            ObjectInitializer.initProperties(response);
            dumper.dump("admin.apps.restrict", response);
        }
        {
            AdminAppsClearResolutionResponse response = new AdminAppsClearResolutionResponse();
            ObjectInitializer.initProperties(response);
            dumper.dump("admin.apps.clearResolution", response);
        }
        {
            AdminAppsUninstallResponse response = new AdminAppsUninstallResponse();
            ObjectInitializer.initProperties(response);
            dumper.dump("admin.apps.uninstall", response);
        }
    }

    @Test
    public void assistant_threads() throws Exception {
        AssistantThreadsSetStatusResponse setStatus = new AssistantThreadsSetStatusResponse();
        ObjectInitializer.initProperties(setStatus);
        dumper.dump("assistant.threads.setStatus", setStatus);
        AssistantThreadsSetSuggestedPromptsResponse setSuggestedPrompts = new AssistantThreadsSetSuggestedPromptsResponse();
        ObjectInitializer.initProperties(setSuggestedPrompts);
        dumper.dump("assistant.threads.setSuggestedPrompts", setSuggestedPrompts);
        AssistantThreadsSetTitleResponse setTitle = new AssistantThreadsSetTitleResponse();
        ObjectInitializer.initProperties(setTitle);
        dumper.dump("assistant.threads.setTitle", setTitle);
    }
}
