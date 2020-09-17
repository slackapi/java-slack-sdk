package test_locally.api.audit;

import com.slack.api.audit.response.ActionsResponse;
import com.slack.api.audit.response.LogsResponse;
import com.slack.api.audit.response.SchemasResponse;
import com.slack.api.util.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import util.FileReader;

import static test_locally.api.util.FieldVerification.verifyIfAllGettersReturnNonNull;
import static test_locally.api.util.FieldVerification.verifyIfAllGettersReturnNonNullRecursively;

@Slf4j
public class FieldsTest {

    private FileReader reader = new FileReader("../json-logs/samples/audit");

    @Test
    public void actions() throws Exception {
        String json = reader.readWholeAsString("/v1/actions.json");
        ActionsResponse obj = GsonFactory.createSnakeCase().fromJson(json, ActionsResponse.class);
        verifyIfAllGettersReturnNonNull(obj, "getRawBody", "getWarning");
        verifyIfAllGettersReturnNonNullRecursively(obj.getActions());
    }

    @Test
    public void logs() throws Exception {
        String json = reader.readWholeAsString("/v1/logs.json");
        LogsResponse obj = GsonFactory.createSnakeCase().fromJson(json, LogsResponse.class);
        verifyIfAllGettersReturnNonNull(obj, "getRawBody", "getWarning");
        verifyIfAllGettersReturnNonNullRecursively(obj.getResponseMetadata(), "getMessages", "getWarnings");
        verifyIfAllGettersReturnNonNull(obj.getEntries().get(0), "getDetails");
        verifyIfAllGettersReturnNonNullRecursively(obj.getEntries().get(0).getActor());
        verifyIfAllGettersReturnNonNullRecursively(obj.getEntries().get(0).getContext());
        verifyIfAllGettersReturnNonNullRecursively(obj.getEntries().get(0).getEntity(),
                "getApp",
                "getEnterprise",
                "getFile",
                "getUsergroup",
                "getChannel",
                "getWorkspace",
                "getWorkflow"
        );
    }

    @Test
    public void schemas() throws Exception {
        String json = reader.readWholeAsString("/v1/schemas.json");
        SchemasResponse obj = GsonFactory.createSnakeCase().fromJson(json, SchemasResponse.class);
        verifyIfAllGettersReturnNonNull(obj, "getRawBody", "getWarning");
        verifyIfAllGettersReturnNonNullRecursively(obj.getSchemas().get(0));
    }
}
