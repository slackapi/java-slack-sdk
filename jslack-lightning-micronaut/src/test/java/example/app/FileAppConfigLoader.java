package example.app;

import com.github.seratch.jslack.lightning.AppConfig;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.inject.Provider;
import javax.inject.Singleton;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Singleton
public class FileAppConfigLoader implements Provider<AppConfig> {

    public static final AppConfig load() {
        AppConfig config = new AppConfig();
        try (InputStream is = FileAppConfigLoader.class.getClassLoader().getResourceAsStream("appConfig.json");
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

    @Override
    public AppConfig get() {
        return load();
    }
}
