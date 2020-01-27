package test_with_remote_apis.events_subscription;

import com.slack.api.app_backend.events.EventsDispatcher;
import com.slack.api.app_backend.events.handler.MessageHandler;
import com.slack.api.app_backend.events.payload.MessagePayload;
import com.slack.api.app_backend.events.servlet.SlackEventsApiServlet;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import javax.servlet.annotation.WebServlet;

public class SimpleEventsApiBackend {

    @Slf4j
    @WebServlet
    public static class SlackEventsServlet extends SlackEventsApiServlet {
        @Override
        protected void setupDispatcher(EventsDispatcher dispatcher) {
            dispatcher.register(new MessageHandler() {
                @Override
                public void handle(MessagePayload payload) {
                    log.info("payload: {}", payload);
                }
            });
        }
    }

    // https://www.eclipse.org/jetty/documentation/current/embedding-jetty.html

    public static void main(String[] args) throws Exception {
        Server server = new Server(3000);
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(SlackEventsServlet.class, "/slack/events");
        server.start();
        server.join();
    }
}

