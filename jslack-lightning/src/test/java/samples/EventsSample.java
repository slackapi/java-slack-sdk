package samples;

import com.github.seratch.jslack.api.model.event.MessageEvent;
import com.github.seratch.jslack.app_backend.events.handler.MessageHandler;
import com.github.seratch.jslack.app_backend.events.payload.MessagePayload;
import com.github.seratch.jslack.lightning.App;
import com.github.seratch.jslack.lightning.AppConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventsSample {

    public static void main(String[] args) throws Exception {
        AppConfig config = ResourceLoader.loadAppConfig();
        App app = new App(config);

        app.event(MessageEvent.class, (event, ctx) -> {
            log.info("message event (LightningEventHandler) - {}", event);
            return ctx.ack();
        });
        app.event(new MessageHandler() {
            @Override
            public void handle(MessagePayload payload) {
                log.info("message event (MessageHandler) - {}", payload);
            }
        });

        TestSlackAppServer server = new TestSlackAppServer(app);
        server.start();
    }

}
