package functions;

import com.slack.api.bolt.App;
import com.slack.api.bolt.google_cloud_functions.SlackApiFunction;

// export SLACK_BOT_TOKEN=
// export SLACK_SIGNING_SECRET=

// Local Development
// mvn function:run
// ngrok http 8080 --subdomain your-domain

// Deployment
// gcloud functions deploy my-first-function --entry-point functions.HelloSlack --runtime java11 --trigger-http --memory 512MB --allow-unauthenticated --set-env-vars SLACK_BOT_TOKEN=$SLACK_BOT_TOKEN,SLACK_SIGNING_SECRET=$SLACK_SIGNING_SECRET

/**
 * Refer to https://cloud.google.com/functions/docs/first-java for details.
 */
public class HelloSlack extends SlackApiFunction {
    private static final App app = new App();

    static {
        app.command("/hello-google-cloud-functions", (req, ctx) -> {
            return ctx.ack("Hi from Google Cloud Functions!");
        });
    }

    public HelloSlack() {
        super(app);
    }
}