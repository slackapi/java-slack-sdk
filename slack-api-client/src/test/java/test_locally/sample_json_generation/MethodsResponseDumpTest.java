package test_locally.sample_json_generation;

import com.slack.api.methods.response.dialog.DialogOpenResponse;
import com.slack.api.methods.response.migration.MigrationExchangeResponse;
import com.slack.api.methods.response.users.UsersGetPresenceResponse;
import com.slack.api.methods.response.users.UsersIdentityResponse;
import com.slack.api.methods.response.views.ViewsOpenResponse;
import com.slack.api.methods.response.views.ViewsPublishResponse;
import com.slack.api.methods.response.views.ViewsPushResponse;
import com.slack.api.methods.response.views.ViewsUpdateResponse;
import com.slack.api.model.ErrorResponseMetadata;
import com.slack.api.model.dialog.DialogResponseMetadata;
import com.slack.api.model.view.View;
import org.junit.Test;
import util.ObjectInitializer;
import util.sample_json_generation.ObjectToJsonDumper;
import util.sample_json_generation.SampleObjects;

import java.util.Arrays;
import java.util.HashMap;

public class MethodsResponseDumpTest {
    // This test class generates sample JSON data for the Web APIs
    // that are not so easy to run in tests.
    // (e.g., the ones requiring trigger_id for running)

    ObjectToJsonDumper dumper = new ObjectToJsonDumper("../json-logs/samples/api");

    static ErrorResponseMetadata errorResponseMetadata = ObjectInitializer.initProperties(new ErrorResponseMetadata());
    static View view = ObjectInitializer.initProperties(new View());
    static {
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
        response.setUserIdMap(new HashMap<>());
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
}
