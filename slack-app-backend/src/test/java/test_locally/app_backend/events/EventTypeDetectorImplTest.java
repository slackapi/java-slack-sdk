package test_locally.app_backend.events;

import com.slack.api.app_backend.events.EventTypeExtractor;
import com.slack.api.app_backend.events.EventTypeExtractorImpl;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EventTypeDetectorImplTest {

    private EventTypeExtractor eventTypeExtractor = new EventTypeExtractorImpl();

    @Test
    public void notFound() {
        {
            String eventType = eventTypeExtractor.extractEventType("{}");
            assertThat(eventType, is(""));
        }
        {
            String eventType = eventTypeExtractor.extractEventType("{\"event\": {}}");
            assertThat(eventType, is(""));
        }
        {
            String eventType = eventTypeExtractor.extractEventSubtype("{}");
            assertThat(eventType, is(""));
        }
        {
            String eventType = eventTypeExtractor.extractEventSubtype("{\"event\": {}}");
            assertThat(eventType, is(""));
        }
    }

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

    @Test
    public void issue_489() {
        String payload = "{\n" +
                "  \"authed_users\": [\n" +
                "    \"RETRACTED\"\n" +
                "  ],\n" +
                "  \"event_id\": \"RETRACTED\",\n" +
                "  \"api_app_id\": \"RETRACTED\",\n" +
                "  \"team_id\": \"RETRACTED\",\n" +
                "  \"event\": {\n" +
                "    \"client_msg_id\": \"b5ac4998-4526-4d0b-8bb0-7bd614c1b774\",\n" +
                "    \"blocks\": [\n" +
                "      {\n" +
                "        \"elements\": [\n" +
                "          {\n" +
                "            \"elements\": [\n" +
                "              {\n" +
                "                \"text\": \"test\",\n" +
                "                \"type\": \"text\"\n" +
                "              }\n" +
                "            ],\n" +
                "            \"type\": \"rich_text_section\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"type\": \"rich_text\",\n" +
                "        \"block_id\": \"Ws3Y\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"event_ts\": \"1592257234.003200\",\n" +
                "    \"channel\": \"RETRACTED\",\n" +
                "    \"text\": \"test\",\n" +
                "    \"team\": \"RETRACTED\",\n" +
                "    \"type\": \"message\",\n" +
                "    \"channel_type\": \"channel\",\n" +
                "    \"user\": \"RETRACTED\",\n" +
                "    \"ts\": \"1592257234.003200\"\n" +
                "  },\n" +
                "  \"type\": \"event_callback\",\n" +
                "  \"event_time\": 1592257234,\n" +
                "  \"token\": \"RETRACTED\"\n" +
                "}";
        String eventType = eventTypeExtractor.extractEventType(payload);
        assertThat(eventType, is("message"));
    }

}
