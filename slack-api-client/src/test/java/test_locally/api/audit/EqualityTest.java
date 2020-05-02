package test_locally.api.audit;

import com.slack.api.audit.AuditApiErrorResponse;
import com.slack.api.audit.AuditApiException;
import com.slack.api.audit.response.ActionsResponse;
import com.slack.api.audit.response.LogsResponse;
import com.slack.api.audit.response.SchemasResponse;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EqualityTest {

    @Test
    public void test() {
        Request request = new Request.Builder()
                .url("https://www.example.com/")
                .build();
        Response response = new Response.Builder()
                .request(request)
                .code(200)
                .protocol(Protocol.HTTP_1_1)
                .message("")
                .build();

        assertEquals(new AuditApiErrorResponse(), new AuditApiErrorResponse());
        assertEquals(new AuditApiException(response, "test"), new AuditApiException(response, "test"));
        assertEquals(new ActionsResponse(), new ActionsResponse());
        assertEquals(new LogsResponse(), new LogsResponse());
        assertEquals(new SchemasResponse(), new SchemasResponse());
    }
}
