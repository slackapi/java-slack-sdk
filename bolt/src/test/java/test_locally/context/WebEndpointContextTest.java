package test_locally.context;

import com.slack.api.bolt.context.WebEndpointContext;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class WebEndpointContextTest {

    @Test
    public void test() {
        WebEndpointContext context = new WebEndpointContext();
        assertNotNull(context.ack());

        Map<String, String> obj = new HashMap<>();
        obj.put("display_name", "seratch");
        assertNotNull(context.ackWithJson(obj));
        assertNotNull(context.ack(context.toJson(obj)));
        assertNotNull(context.toJson(obj));
    }
}
