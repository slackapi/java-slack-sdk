package example.app;

import com.github.seratch.jslack.lightning.App;
import com.github.seratch.jslack.lightning.AppConfig;

import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class AppProvider implements Provider<App> {

    private AppConfig config;

    public AppProvider(AppConfig appConfig) {
        this.config = appConfig;
    }

    @Override
    public App get() {
        App app = new App(config);
        app.command("/hello", (req, ctx) -> {
            return ctx.ack(r -> r.text("Thanks!"));
        });
        app.start();
        return app;
    }
}
