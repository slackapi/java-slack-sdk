package test_locally.context;

import com.slack.api.bolt.context.builtin.OutgoingWebhooksContext;
import com.slack.api.bolt.response.Response;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OutgoingWebhooksContextTest {

    @Test
    public void ack() {
        OutgoingWebhooksContext context = new OutgoingWebhooksContext();
        Response response = context.ack(r -> r.text("I got it!"));
        assertEquals(200L, response.getStatusCode().longValue());
    }
}
