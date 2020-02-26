package test_locally.context;

import com.slack.api.bolt.context.builtin.ViewSubmissionContext;
import com.slack.api.bolt.response.Response;
import com.slack.api.model.view.View;
import org.junit.Test;

import java.util.Collections;

import static com.slack.api.model.view.Views.view;
import static org.junit.Assert.assertEquals;

public class ViewSubmissionContextTest {

    View view = view(r -> r.type("modal").callbackId("callback"));

    @Test
    public void ack_viewAsString() {
        ViewSubmissionContext context = new ViewSubmissionContext();
        Response response = context.ack("update", "{}");
        assertEquals(200L, response.getStatusCode().longValue());
    }

    @Test
    public void ack_view() {
        ViewSubmissionContext context = new ViewSubmissionContext();
        Response response = context.ack("update", view);
        assertEquals(200L, response.getStatusCode().longValue());
    }

    @Test
    public void ackWithUpdate() {
        ViewSubmissionContext context = new ViewSubmissionContext();
        {
            Response response = context.ackWithUpdate("{}");
            assertEquals(200L, response.getStatusCode().longValue());
        }
        {
            Response response = context.ackWithUpdate(view);
            assertEquals(200L, response.getStatusCode().longValue());
        }
    }

    @Test
    public void ackWithPush() {
        ViewSubmissionContext context = new ViewSubmissionContext();
        {
            Response response = context.ackWithPush("{}");
            assertEquals(200L, response.getStatusCode().longValue());
        }
        {
            Response response = context.ackWithPush(view);
            assertEquals(200L, response.getStatusCode().longValue());
        }
    }

    @Test
    public void ackWithErrors() {
        ViewSubmissionContext context = new ViewSubmissionContext();
        Response response = context.ackWithErrors(Collections.emptyMap());
        assertEquals(200L, response.getStatusCode().longValue());
    }

}
