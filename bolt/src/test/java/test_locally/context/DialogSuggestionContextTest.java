package test_locally.context;

import com.slack.api.bolt.context.builtin.DialogSuggestionContext;
import com.slack.api.bolt.response.Response;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class DialogSuggestionContextTest {

    @Test
    public void ack() {
        DialogSuggestionContext context = new DialogSuggestionContext();
        Response response = context.ack(r -> r.options(Collections.emptyList()));
        assertEquals(200L, response.getStatusCode().longValue());
    }
}
