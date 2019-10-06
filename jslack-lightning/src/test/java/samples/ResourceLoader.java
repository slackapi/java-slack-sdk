package samples;

import com.github.seratch.jslack.lightning.AppConfig;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.util.stream.Collectors.joining;

@Slf4j
public class ResourceLoader {

    private ResourceLoader() {
    }

    public static AppConfig loadAppConfig() {
        AppConfig config = new AppConfig();
        ClassLoader classLoader = DialogSample.class.getClassLoader();
        // https://github.com/seratch/jslack/blob/master/jslack-lightning/src/test/resources/
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

    public static String load(String filepath) {
        ClassLoader classLoader = DialogSample.class.getClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(filepath);
             InputStreamReader isr = new InputStreamReader(is)) {
            return new BufferedReader(isr).lines().collect(joining());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

}
