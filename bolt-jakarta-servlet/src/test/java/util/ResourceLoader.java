package util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.slack.api.bolt.AppConfig;
import config.Constants;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.joining;

@Slf4j
public class ResourceLoader {

    private ResourceLoader() {
    }

    public static AppConfig loadAppConfig(String fileName) {
        AppConfig config = new AppConfig();
        ClassLoader classLoader = Constants.class.getClassLoader();
        // https://github.com/slackapi/java-slack-sdk/blob/main/bolt-jakarta-servlet/src/test/resources
        try (InputStream is = classLoader.getResourceAsStream(fileName)) {
            if (is != null) {
                try (InputStreamReader isr = new InputStreamReader(is)) {
                    String json = new BufferedReader(isr).lines().collect(joining());
                    JsonObject j = new Gson().fromJson(json, JsonElement.class).getAsJsonObject();
                    config.setSigningSecret(j.get("signingSecret").getAsString());
                    if (j.get("singleTeamBotToken") != null) {
                        config.setSingleTeamBotToken(j.get("singleTeamBotToken").getAsString());
                    }
                    if (j.get("clientId") != null) {
                        config.setClientId(j.get("clientId").getAsString());
                    }
                    if (j.get("clientSecret") != null) {
                        config.setClientSecret(j.get("clientSecret").getAsString());
                    }
                    if (j.get("scope") != null) {
                        config.setScope(j.get("scope").getAsString());
                    }
                    if (j.get("userScope") != null) {
                        config.setUserScope(j.get("userScope").getAsString());
                    }
                    if (j.get("oauthCompletionUrl") != null) {
                        config.setOauthCompletionUrl(j.get("oauthCompletionUrl").getAsString());
                    }
                    if (j.get("oauthInstallPath") != null) {
                        config.setOauthInstallPath(j.get("oauthInstallPath").getAsString());
                    }
                    if (j.get("oauthRedirectUriPath") != null) {
                        config.setOauthRedirectUriPath(j.get("oauthRedirectUriPath").getAsString());
                    }
                    if (j.get("redirectUri") != null) {
                        config.setRedirectUri(j.get("redirectUri").getAsString());
                    }
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return config;
    }

    public static AppConfig loadAppConfig() {
        return loadAppConfig("appConfig.json");
    }

    public static Map<String, String> loadValues() {
        ClassLoader classLoader = Constants.class.getClassLoader();
        // src/test/resources
        try (InputStream is = classLoader.getResourceAsStream("appConfig.json")) {
            if (is == null) {
                throw new RuntimeException("Place src/test/resources/appConfig.json!");
            }
            try (InputStreamReader isr = new InputStreamReader(is)) {
                String json = new BufferedReader(isr).lines().collect(joining());
                return new Gson().fromJson(json, HashMap.class);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public static String load(String filepath) {
        ClassLoader classLoader = Constants.class.getClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(filepath);
             InputStreamReader isr = new InputStreamReader(is)) {
            return new BufferedReader(isr).lines().collect(joining());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

}
