package test_locally.docs;

import com.slack.api.bolt.App;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.bolt.service.OAuthStateService;
import com.slack.api.bolt.service.builtin.AmazonS3InstallationService;
import com.slack.api.bolt.service.builtin.AmazonS3OAuthStateService;

public class OAuthTest {

    public static void main(String[] args) {

        // The standard AWS env variables are expected
        // export AWS_REGION=us-east-1
        // export AWS_ACCESS_KEY_ID=AAAA*************
        // export AWS_SECRET_ACCESS_KEY=4o7***********************

        // Please be careful about the security policies on this bucket.
        String awsS3BucketName = "YOUR_OWN_BUCKET_NAME_HERE";

        InstallationService installationService = new AmazonS3InstallationService(awsS3BucketName);
        // Set true if you'd like to store every single installation as a different record
        installationService.setHistoricalDataEnabled(true);

        // apiApp uses only InstallationService to access stored tokens
        App apiApp = new App();
        apiApp.command("/hi", (req, ctx) -> {
            return ctx.ack("Hi there!");
        });
        apiApp.service(installationService);

        // Needless to say, oauthApp uses InstallationService
        // In addition, it uses OAuthStateService to create/read/delete state parameters
        App oauthApp = new App().asOAuthApp(true);
        oauthApp.service(installationService);

        // Store valid state parameter values in Amazon S3 storage
        OAuthStateService stateService = new AmazonS3OAuthStateService(awsS3BucketName);
        // This service is necessary only for OAuth flow apps
        oauthApp.service(stateService);

        oauthApp.endpoint("GET", "/slack/oauth/completion", (req, ctx) -> {
            return Response.builder()
                    .statusCode(200)
                    .contentType("text/html")
                    .body(renderCompletionPageHtml(req.getQueryString()))
                    .build();
        });

        oauthApp.endpoint("GET", "/slack/oauth/cancellation", (req, ctx) -> {
            return Response.builder()
                    .statusCode(200)
                    .contentType("text/html")
                    .body(renderCancellationPageHtml(req.getQueryString()))
                    .build();
        });
    }

    static String renderCompletionPageHtml(String queryString) {
        return null;
    }

    static String renderCancellationPageHtml(String queryString) {
        return null;
    }

}
