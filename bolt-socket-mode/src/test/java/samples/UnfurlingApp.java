package samples;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import com.slack.api.methods.request.chat.ChatUnfurlRequest;
import com.slack.api.model.event.LinkSharedEvent;

import java.util.HashMap;
import java.util.Map;

public class UnfurlingApp {

    public static void main(String[] args) throws Exception {
        App app = new App(AppConfig.builder().singleTeamBotToken(System.getenv("SLACK_BOT_TOKEN")).build());
        app.use((req, resp, chain) -> {
            req.getContext().logger.info(req.getRequestBodyAsString());
            return chain.next(req);
        });

        app.event(LinkSharedEvent.class, (payload, ctx) -> {
            app.executorService().submit(() -> {
                try {
                    Map<String, ChatUnfurlRequest.UnfurlDetail> unfurls = new HashMap<>();
                    for (LinkSharedEvent.Link link : payload.getEvent().getLinks()) {
                        ChatUnfurlRequest.UnfurlDetail unfurl = new ChatUnfurlRequest.UnfurlDetail();
                        unfurl.setTitle("Collaborate & Create Amazing Graphic Design for Free");
                        unfurl.setText("text text text");
                        unfurls.put(link.getUrl(), unfurl);
                    }
                    ctx.client().chatUnfurl(r -> r
                            .channel(payload.getEvent().getChannel())
                            .ts(payload.getEvent().getMessageTs())
                            .source(payload.getEvent().getSource())
                            .unfurls(unfurls)
                    );
                } catch (Exception e) {
                    ctx.logger.error("Failed to unfurl the links", e);
                }
            });
            return ctx.ack();
        });

        String appToken = System.getenv("SLACK_APP_TOKEN");
        SocketModeApp socketModeApp = new SocketModeApp(appToken, app);
        socketModeApp.start();
    }
}
