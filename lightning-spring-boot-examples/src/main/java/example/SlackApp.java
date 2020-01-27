package example;

import com.slack.api.lightning.App;
import com.slack.api.lightning.AppConfig;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.util.stream.Collectors.joining;

@Configuration
@Slf4j
public class SlackApp {

//    @Bean
//    public AppConfig loadDefaultConfig() {
//        return AppConfig.builder().build();
//    }

    @Bean
    public AppConfig loadAppConfig() {
        AppConfig config = new AppConfig();
        ClassLoader classLoader = SlackApp.class.getClassLoader();
        // src/test/resources/appConfig.json
        try (InputStream is = classLoader.getResourceAsStream("appConfig.json");
             InputStreamReader isr = new InputStreamReader(is)) {
            String json = new BufferedReader(isr).lines().collect(joining());
            JsonObject j = new Gson().fromJson(json, JsonElement.class).getAsJsonObject();
            config.setSigningSecret(j.get("signingSecret").getAsString());
            config.setSingleTeamBotToken(j.get("singleTeamBotToken").getAsString());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return config;
    }

    @Bean
    public App initSlackApp(AppConfig config) {
        App app = new App(config);
        app.command("/hello", (req, ctx) -> {
            return ctx.ack(r -> r.text("Thanks!"));
        });
        return app;
    }

}
