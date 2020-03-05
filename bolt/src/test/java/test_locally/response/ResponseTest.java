package test_locally.response;

import com.slack.api.bolt.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ResponseTest {

    @Test
    public void ok() {
        Map<String, String> obj = new HashMap<>();
        obj.put("error", "invalid_arguments");
        Response response = Response.ok(obj);
        assertNotNull(response);
        assertEquals(200L, response.getStatusCode().longValue());
    }
}
