package test_locally.app;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class OAuthCallbacksTest {

    @Test
    public void persistenceCallback() {
        App app = new App(AppConfig.builder()
                .signingSecret("secret")
                .clientId("111.222")
                .clientSecret("secret")
                .scope("commands,chat:write")
                .build());
        app.oauthPersistenceCallback(args -> args.getResponse().setStatusCode(200));
        app.oauthPersistenceErrorCallback(args -> args.getResponse().setStatusCode(404));

        // TODO: assertions
    }
}
