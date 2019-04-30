package app_backend.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.github.seratch.jslack.app_backend.events.EventsDispatcher;
import com.github.seratch.jslack.app_backend.events.EventsDispatcherFactory;
import com.github.seratch.jslack.app_backend.events.handler.MessageHandler;
import com.github.seratch.jslack.app_backend.events.handler.ReactionAddedHandler;
import com.github.seratch.jslack.app_backend.events.payload.MessagePayload;
import com.github.seratch.jslack.app_backend.events.payload.ReactionAddedPayload;
import com.github.seratch.jslack.app_backend.events.payload.UrlVerificationPayload;
import com.github.seratch.jslack.app_backend.vendor.aws.lambda.request.ApiGatewayRequest;
import com.github.seratch.jslack.app_backend.vendor.aws.lambda.response.ApiGatewayResponse;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class EventsHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

    private static final EventsDispatcher EVENT_DISPATCHER = EventsDispatcherFactory.getInstance();

    static {
        EVENT_DISPATCHER.register(new MessageHandler() {
            @Override
            public void handle(MessagePayload payload) {
                log.info("message: {}", payload.getEvent());
            }
        });
        EVENT_DISPATCHER.register(new ReactionAddedHandler() {
            @Override
            public void handle(ReactionAddedPayload payload) {
                log.info("Reaction {} is added to {}", payload.getEvent().getReaction(), payload.getEvent().getItem());
            }
        });
    }

    @Override
    public ApiGatewayResponse handleRequest(ApiGatewayRequest request, Context context) {

        JsonObject payload = toJsonObject(request);
        String eventType = payload.get("type").getAsString();
        if (UrlVerificationPayload.TYPE.equals(eventType)) {
            return handleUrlVerification(payload);
        } else {
            // If you have some heavy operations which may take a bit long, doing it asynchronously would be preferable
            EVENT_DISPATCHER.enqueue(request.getBody());

            // NOTE: https://api.slack.com/events-api#responding_to_events
            // Your app should respond to the event request with an HTTP 2xx within three seconds.
            // If it does not, we'll consider the event delivery attempt failed.
            // After a failure, we'll retry three times, backing off exponentially.
            return ApiGatewayResponse.builder().setStatusCode(200).build();
        }
    }

    private JsonObject toJsonObject(ApiGatewayRequest request) {
        return GsonFactory.createSnakeCase()
                .fromJson(request.getBody(), JsonElement.class)
                .getAsJsonObject();
    }

    private ApiGatewayResponse handleUrlVerification(JsonObject payload) {
        // url_verification: https://api.slack.com/events/url_verification
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/plain");
        return ApiGatewayResponse.builder()
                .setStatusCode(200)
                .setHeaders(headers)
                .setObjectBody(payload.get("challenge").getAsString())
                .build();
    }

}
