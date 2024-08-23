package test_with_remote_apis.jakarta_socket_mode;

import com.slack.api.jakarta_socket_mode.JakartaSocketModeClientFactory;
import com.slack.api.socket_mode.SocketModeClient;
import com.slack.api.socket_mode.request.EventsApiEnvelope;
import com.slack.api.socket_mode.response.AckResponse;
import com.slack.api.socket_mode.response.SocketModeResponse;

import java.io.IOException;

public class SocketModeSnippet {

    public static void main(String[] args) throws IOException {
        String appLevelToken = "xapp-A111-222-xxx";

        // Issue a new WSS URL and set the value to the client
        try (SocketModeClient client = JakartaSocketModeClientFactory.create(appLevelToken)) {
            // SocketModeClient has #close() method

            // Add a listener function to handle all raw WebSocket text messages
            // You can handle not only envelopes but also any others such as "hello" messages.
            client.addWebSocketMessageListener((String message) -> {
                // TODO: Do something with the raw WebSocket text message
            });

            client.addWebSocketErrorListener((Throwable reason) -> {
                // TODO: Do something with a thrown exception
            });

            // Add a listener function that handles only type: events envelopes
            client.addEventsApiEnvelopeListener((EventsApiEnvelope envelope) -> {
                // TODO: Do something with the payload

                // Acknowledge the request
                SocketModeResponse ack = AckResponse.builder().envelopeId(envelope.getEnvelopeId()).build();
                client.sendSocketModeResponse(ack);
            });

            client.connect(); // Start receiving messages from the Socket Mode server

            client.disconnect(); // Disconnect from the server

            client.connectToNewEndpoint(); // Issue a new WSS URL and connects to the URL
        }
    }
}
