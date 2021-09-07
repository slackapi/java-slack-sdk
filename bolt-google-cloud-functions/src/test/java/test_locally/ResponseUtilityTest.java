package test_locally;

import com.slack.api.bolt.response.Response;
import org.junit.Test;

import static com.slack.api.bolt.google_cloud_functions.SlackApiFunction.buildNotNullResponseBody;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ResponseUtilityTest {

    @Test
    public void toNotNullResponseBody_null() {
        Response response = Response.builder().statusCode(200).body(null).build();
        String textBody = buildNotNullResponseBody(response);
        assertNotNull(textBody);
    }

    @Test
    public void toNotNullResponseBody_some_value() {
        Response response = Response.builder().statusCode(200).body("ok").build();
        String textBody = buildNotNullResponseBody(response);
        assertEquals("ok", textBody);
    }
}
