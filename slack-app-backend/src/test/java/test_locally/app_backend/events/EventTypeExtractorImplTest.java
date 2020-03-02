package test_locally.app_backend.events;

import com.slack.api.app_backend.events.EventTypeExtractorImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EventTypeExtractorImplTest {

    // https://api.slack.com/events/app_home_opened
    String appHomeOpened = "{ \n" +
            "  \"type\": \"app_home_opened\",\n" +
            "  \"user\": \"U061F7AUR\",\n" +
            "  \"channel\": \"D0LAN2Q65\",\n" +
            "  \"event_ts\": \"1515449522000016\",\n" +
            "  \"tab\": \"home\",\n" +
            "  \"view\": { \n" +
            "    \"id\": \"VPASKP233\",\n" +
            "    \"team_id\": \"T21312902\",\n" +
            "    \"type\": \"home\",\n" +
            "    \"blocks\": [],\n" +
            "    \"private_metadata\": \"\",\n" +
            "    \"callback_id\": \"\",\n" +
            "    \"state\":{},\n" +
            "    \"hash\":\"1231232323.12321312\",\n" +
            "    \"clear_on_close\": false,\n" +
            "    \"notify_on_close\": false,\n" +
            "    \"root_view_id\": \"VPASKP233\",\n" +
            "    \"app_id\": \"A21SDS90\",\n" +
            "    \"external_id\": \"\",\n" +
            "    \"app_installed_team_id\": \"T21312902\",\n" +
            "    \"bot_id\": \"BSDKSAO2\"\n" +
            "  }\n" +
            "}";

    // https://api.slack.com/events/message/bot_message
    String botMessage = "{\n" +
            "  \"type\": \"message\",\n" +
            "  \"subtype\": \"bot_message\",\n" +
            "  \"ts\": \"1358877455.000010\",\n" +
            "  \"text\": \"Pushing is the answer\",\n" +
            "  \"bot_id\": \"BB12033\",\n" +
            "  \"username\": \"github\",\n" +
            "  \"icons\": {}\n" +
            "}";

    String toPayload(String event) {
        return "{\n" +
                "  \"token\": \"XXYYZZ\",\n" +
                "  \"team_id\": \"TXXXXXXXX\",\n" +
                "  \"api_app_id\": \"AXXXXXXXXX\",\n" +
                "  \"event\": " + event + ",\n" +
                "  \"type\": \"event_callback\",\n" +
                "  \"authed_users\": [\n" +
                "    \"UXXXXXXX1\",\n" +
                "    \"UXXXXXXX2\"\n" +
                "  ],\n" +
                "  \"event_id\": \"Ev08MFMKH6\",\n" +
                "  \"event_time\": 1234567890\n" +
                "}";
    }

    @Test
    public void extractEventType_app_home_opened() {
        EventTypeExtractorImpl extractor = new EventTypeExtractorImpl();
        String type = extractor.extractEventType(toPayload(appHomeOpened));
        assertEquals("app_home_opened", type);
    }

    @Test
    public void extractEventType_message() {
        EventTypeExtractorImpl extractor = new EventTypeExtractorImpl();
        String type = extractor.extractEventType(toPayload(botMessage));
        assertEquals("message", type);
    }

    @Test
    public void extractEventSubtype() {
        EventTypeExtractorImpl extractor = new EventTypeExtractorImpl();
        String type = extractor.extractEventSubtype(toPayload(botMessage));
        assertEquals("bot_message", type);
    }
}
