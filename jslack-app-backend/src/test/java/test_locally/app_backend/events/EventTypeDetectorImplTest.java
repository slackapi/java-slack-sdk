package test_locally.app_backend.events;

import com.github.seratch.jslack.app_backend.events.EventTypeExtractor;
import com.github.seratch.jslack.app_backend.events.EventTypeExtractorImpl;
import com.github.seratch.jslack.app_backend.events.EventsDispatcherImpl;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EventTypeDetectorImplTest {

    private EventTypeExtractor eventTypeExtractor = new EventTypeExtractorImpl();

    @Test
    public void detect_goodbye() {
        String payload = "{\n" +
                "        \"token\": \"XXYYZZ\",\n" +
                "        \"team_id\": \"TXXXXXXXX\",\n" +
                "        \"api_app_id\": \"AXXXXXXXXX\",\n" +
                "        \"event\": {\n" +
                "                \"type\": \"goodbye\"\n" +
                "        },\n" +
                "        \"type\": \"event_callback\",\n" +
                "        \"authed_users\": [\n" +
                "                \"UXXXXXXX1\",\n" +
                "                \"UXXXXXXX2\"\n" +
                "        ],\n" +
                "        \"event_id\": \"Ev08MFMKH6\",\n" +
                "        \"event_time\": 1234567890\n" +
                "}";
        String eventType = eventTypeExtractor.extractEventType(payload);
        assertThat(eventType, is("goodbye"));
    }

    @Test
    public void detect_escape() {
        String payload = "{\n" +
                "        \"token\": \"XXYYZZ\",\n" +
                "        \"team_id\": \"TXXXXXXXX\",\n" +
                "        \"text\": \"The \\\" should be ignored\",\n" +
                "        \"api_app_id\": \"AXXXXXXXXX\",\n" +
                "        \"type\": \"event_callback\",\n" +
                "        \"event\": {\n" +
                "                \"type\": \"message\"\n" +
                "        },\n" +
                "        \"authed_users\": [\n" +
                "                \"UXXXXXXX1\",\n" +
                "                \"UXXXXXXX2\"\n" +
                "        ],\n" +
                "        \"event_id\": \"Ev08MFMKH6\",\n" +
                "        \"event_time\": 1234567890\n" +
                "}";
        String eventType = eventTypeExtractor.extractEventType(payload);
        assertThat(eventType, is("message"));
    }

}