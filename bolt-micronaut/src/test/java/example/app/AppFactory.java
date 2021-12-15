package example.app;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Factory
public class AppFactory {

    @Singleton
    public AppConfig createAppConfig() {
        AppConfig config = new AppConfig();
        try (InputStream is = AppFactory.class.getClassLoader().getResourceAsStream("appConfig.json");
             InputStreamReader isr = new InputStreamReader(is)) {
            String json = new BufferedReader(isr).lines().collect(Collectors.joining());
            JsonObject j = new Gson().fromJson(json, JsonElement.class).getAsJsonObject();
            config.setSigningSecret(j.get("signingSecret").getAsString());
            config.setSingleTeamBotToken(j.get("singleTeamBotToken").getAsString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return config;
    }

    @Singleton
    public App createApp(AppConfig config) {
        App app = new App(config);
        app.command("/hello", (req, ctx) -> {
            return ctx.ack(r -> r.text("Thanks!"));
        });
        app.command(Pattern.compile("/submission-no.\\d+"), (req, ctx) -> {
            return ctx.ack(r -> r.text(req.getPayload().getCommand()));
        });
        return app;
    }

}
