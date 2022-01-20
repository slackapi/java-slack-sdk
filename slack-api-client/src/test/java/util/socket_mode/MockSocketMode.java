package util.socket_mode;

import com.slack.api.util.thread.DaemonThreadExecutorServiceProvider;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.StatusCode;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MockSocketMode extends WebSocketAdapter {
    private CountDownLatch closureLatch = new CountDownLatch(1);

    private CopyOnWriteArrayList<Session> activeSessions = new CopyOnWriteArrayList<>();
    private ScheduledExecutorService service = DaemonThreadExecutorServiceProvider.getInstance()
            .createThreadScheduledExecutor(MockSocketMode.class.getCanonicalName());

    public MockSocketMode() {
        super();
        service.scheduleAtFixedRate(() -> {
            List<Session> stoleSessions = new ArrayList<>();
            for (Session session : activeSessions) {
                if (session.isOpen()) {
                    try {
                        session.getRemote().sendString(getRandomEnvelope());
                    } catch (IOException e) {
                        log.error("Failed to send a message", e);
                    }
                } else {
                    stoleSessions.add(session);
                }
            }
            activeSessions.removeAll(stoleSessions);

        }, 200L, 100L, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onWebSocketConnect(Session session) {
        super.onWebSocketConnect(session);
        this.activeSessions.add(session);
        log.info("connected: {}", session);
        try {
            this.getRemote().sendString("{\n" +
                    "  \"type\": \"hello\",\n" +
                    "  \"num_connections\": 1,\n" +
                    "  \"debug_info\": {\n" +
                    "    \"host\": \"applink-xxx-yyy\",\n" +
                    "    \"build_number\": 999,\n" +
                    "    \"approximate_connection_time\": 18060\n" +
                    "  },\n" +
                    "  \"connection_info\": {\n" +
                    "    \"app_id\": \"A111\"\n" +
                    "  }\n" +
                    "}");
        } catch (IOException e) {
            log.error("Failed to send hello message", e);
        }
    }

    @Override
    public void onWebSocketText(String message) {
        super.onWebSocketText(message);
        log.info("text: {}", message);
        if (message.toLowerCase(Locale.US).contains("bye")) {
            getSession().close(StatusCode.NORMAL, "Thanks");
        }
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        super.onWebSocketClose(statusCode, reason);
        log.info("closed: (code: {}, reason: {})", statusCode, reason);
        closureLatch.countDown();
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        super.onWebSocketError(cause);
        cause.printStackTrace(System.err);
    }

    String interactiveEnvelope = "{\n" +
            "  \"envelope_id\": \"xxx-11-222-yyy-zzz\",\n" +
            "  \"payload\": {\n" +
            "    \"type\": \"block_actions\",\n" +
            "    \"user\": {\n" +
            "      \"id\": \"U111\",\n" +
            "      \"username\": \"test-test-test\",\n" +
            "      \"name\": \"test-test-test\",\n" +
            "      \"team_id\": \"T111\"\n" +
            "    },\n" +
            "    \"api_app_id\": \"A111\",\n" +
            "    \"token\": \"fixed-value\",\n" +
            "    \"container\": {\n" +
            "      \"type\": \"message\",\n" +
            "      \"message_ts\": \"1605853634.000400\",\n" +
            "      \"channel_id\": \"C111\",\n" +
            "      \"is_ephemeral\": false\n" +
            "    },\n" +
            "    \"trigger_id\": \"111.222.xxx\",\n" +
            "    \"team\": {\n" +
            "      \"id\": \"T111\",\n" +
            "      \"domain\": \"test-test-test\"\n" +
            "    },\n" +
            "    \"channel\": {\n" +
            "      \"id\": \"C111\",\n" +
            "      \"name\": \"random\"\n" +
            "    },\n" +
            "    \"message\": {\n" +
            "      \"bot_id\": \"B111\",\n" +
            "      \"type\": \"message\",\n" +
            "      \"text\": \"This content can't be displayed.\",\n" +
            "      \"user\": \"U222\",\n" +
            "      \"ts\": \"1605853634.000400\",\n" +
            "      \"team\": \"T111\",\n" +
            "      \"blocks\": [\n" +
            "        {\n" +
            "          \"type\": \"actions\",\n" +
            "          \"block_id\": \"b\",\n" +
            "          \"elements\": [\n" +
            "            {\n" +
            "              \"type\": \"button\",\n" +
            "              \"action_id\": \"a\",\n" +
            "              \"text\": {\n" +
            "                \"type\": \"plain_text\",\n" +
            "                \"text\": \"Click Me!\",\n" +
            "                \"emoji\": true\n" +
            "              },\n" +
            "              \"value\": \"underlying\"\n" +
            "            }\n" +
            "          ]\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    \"response_url\": \"https://hooks.slack.com/actions/T111/111/xxx\",\n" +
            "    \"actions\": [\n" +
            "      {\n" +
            "        \"action_id\": \"a\",\n" +
            "        \"block_id\": \"b\",\n" +
            "        \"text\": {\n" +
            "          \"type\": \"plain_text\",\n" +
            "          \"text\": \"Click Me!\",\n" +
            "          \"emoji\": true\n" +
            "        },\n" +
            "        \"value\": \"underlying\",\n" +
            "        \"type\": \"button\",\n" +
            "        \"action_ts\": \"1605853645.582706\"\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  \"type\": \"interactive\",\n" +
            "  \"accepts_response_payload\": false\n" +
            "}\n";

    String eventsEnvelope = "{\n" +
            "  \"envelope_id\": \"xxx-11-222-yyy-zzz\",\n" +
            "  \"payload\": {\n" +
            "    \"token\": \"fixed-value\",\n" +
            "    \"team_id\": \"T111\",\n" +
            "    \"api_app_id\": \"A111\",\n" +
            "    \"event\": {\n" +
            "      \"client_msg_id\": \"1748313e-912c-4942-a562-99754707692c\",\n" +
            "      \"type\": \"app_mention\",\n" +
            "      \"text\": \"<@U222> hey\",\n" +
            "      \"user\": \"U111\",\n" +
            "      \"ts\": \"1605853844.000800\",\n" +
            "      \"team\": \"T111\",\n" +
            "      \"blocks\": [\n" +
            "        {\n" +
            "          \"type\": \"rich_text\",\n" +
            "          \"block_id\": \"K8xp\",\n" +
            "          \"elements\": [\n" +
            "            {\n" +
            "              \"type\": \"rich_text_section\",\n" +
            "              \"elements\": [\n" +
            "                {\n" +
            "                  \"type\": \"user\",\n" +
            "                  \"user_id\": \"U222\"\n" +
            "                },\n" +
            "                {\n" +
            "                  \"type\": \"text\",\n" +
            "                  \"text\": \" hey\"\n" +
            "                }\n" +
            "              ]\n" +
            "            }\n" +
            "          ]\n" +
            "        }\n" +
            "      ],\n" +
            "      \"channel\": \"C111\",\n" +
            "      \"event_ts\": \"1605853844.000800\"\n" +
            "    },\n" +
            "    \"type\": \"event_callback\",\n" +
            "    \"event_id\": \"Ev01ERKCFKK9\",\n" +
            "    \"event_time\": 1605853844,\n" +
            "    \"authorizations\": [\n" +
            "      {\n" +
            "        \"enterprise_id\": null,\n" +
            "        \"team_id\": \"T111\",\n" +
            "        \"user_id\": \"U222\",\n" +
            "        \"is_bot\": true,\n" +
            "        \"is_enterprise_install\": false\n" +
            "      }\n" +
            "    ],\n" +
            "    \"is_ext_shared_channel\": false,\n" +
            "    \"event_context\": \"1-app_mention-T111-C111\"\n" +
            "  },\n" +
            "  \"type\": \"events_api\",\n" +
            "  \"accepts_response_payload\": false,\n" +
            "  \"retry_attempt\": 0,\n" +
            "  \"retry_reason\": \"\"\n" +
            "}\n";

    String commandEnvelope = "{\n" +
            "  \"envelope_id\": \"xxx-11-222-yyy-zzz\",\n" +
            "  \"payload\": {\n" +
            "    \"token\": \"fixed-value\",\n" +
            "    \"team_id\": \"T111\",\n" +
            "    \"team_domain\": \"test-test-test\",\n" +
            "    \"channel_id\": \"C111\",\n" +
            "    \"channel_name\": \"random\",\n" +
            "    \"user_id\": \"U111\",\n" +
            "    \"user_name\": \"test-test-test\",\n" +
            "    \"command\": \"/hi-socket-mode\",\n" +
            "    \"text\": \"\",\n" +
            "    \"api_app_id\": \"A111\",\n" +
            "    \"response_url\": \"https://hooks.slack.com/commands/T111/111/xxx\",\n" +
            "    \"trigger_id\": \"111.222.xxx\"\n" +
            "  },\n" +
            "  \"type\": \"slash_commands\",\n" +
            "  \"accepts_response_payload\": true\n" +
            "}\n";

    List<String> envelopes = Arrays.asList(
            interactiveEnvelope,
            eventsEnvelope,
            commandEnvelope
    );

    private SecureRandom random = new SecureRandom();

    private String getRandomEnvelope() {
        return envelopes.get(random.nextInt(envelopes.size()));
    }
}