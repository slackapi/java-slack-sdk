package test_with_remote_apis.socket_mode;

import com.slack.api.Slack;
import com.slack.api.socket_mode.SocketModeClient;
import com.slack.api.socket_mode.request.EventsApiEnvelope;
import com.slack.api.socket_mode.response.AckResponse;
import com.slack.api.socket_mode.response.SocketModeResponse;

import java.io.IOException;

public class SocketModeSnippet {

    public static void main(String[] args) throws IOException {
        String appLevelToken = "xapp-A111-222-xxx";

        // Issues a new WSS URL and set the value to the client
        try (SocketModeClient client = Slack.getInstance().socketMode(appLevelToken)) {
            // SocketModeClient has #close() method

            // Adds a listener function to handle all raw WebSocket text messages
            // You can handle not only envelopes but also any others such as "hello" messages.
            client.addWebSocketMessageListener((String message) -> {
                // TODO: Do something with the raw WebSocket text message
            });

            client.addWebSocketErrorListener((Throwable reason) -> {
                // TODO: Do something with a thrown exception
            });

            // Adds a listener function that handles only type: events envelopes
            client.addEventsApiEnvelopeListener((EventsApiEnvelope envelope) -> {
                // TODO: Do something with the payload

                // Acknowledge the request
                SocketModeResponse ack = AckResponse.builder().envelopeId(envelope.getEnvelopeId()).build();
                client.sendSocketModeResponse(ack);
            });

            client.connect(); // Starts receiving messages from the Socket Mode server

            client.disconnect(); // Disconnects from the server

            client.connectToNewEndpoint(); // Issues a new WSS URL and connects to the URL
        }
    }
}
