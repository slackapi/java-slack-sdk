package test_locally.service.oauth;

import com.slack.api.app_backend.oauth.payload.VerificationCodePayload;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.model.Installer;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.OAuthCallbackRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.bolt.service.builtin.FileInstallationService;
import com.slack.api.bolt.service.builtin.oauth.default_impl.OAuthDefaultSuccessHandler;
import com.slack.api.methods.response.oauth.OAuthAccessResponse;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class OAuthDefaultSuccessHandlerTest {

    @Test
    public void completion() {
        InstallationService installationService = new FileInstallationService(new AppConfig(), "target/files");
        OAuthDefaultSuccessHandler handler = new OAuthDefaultSuccessHandler(new AppConfig(), installationService);

        RequestHeaders headers = new RequestHeaders(new HashMap<>());
        OAuthCallbackRequest request = new OAuthCallbackRequest(new HashMap<>(), "", VerificationCodePayload.from(new HashMap<>()), headers);

        Response response = new Response();
        OAuthAccessResponse apiResponse = new OAuthAccessResponse();
        apiResponse.setTeamId("T111");

        Response processedResponse = handler.handle(request, response, apiResponse);
        assertEquals(200, processedResponse.getStatusCode().longValue());
        assertEquals("text/html; charset=utf-8", processedResponse.getContentType());
        assertEquals("<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"refresh\" content=\"0; URL=slack://open\">\n" +
                "<style>\n" +
                "body {\n" +
                "  padding: 10px 15px;\n" +
                "  font-family: verdana;\n" +
                "  text-align: center;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h2>Thank you!</h2>\n" +
                "<p>Redirecting to the Slack App... click <a href=\"slack://open\">here</a>. If you use the browser version of Slack, click <a href=\"https://app.slack.com/client/T111\" target=\"_blank\">this link</a> instead.</p>\n" +
                "</body>\n" +
                "</html>", processedResponse.getBody());
    }

    @Test
    public void completion_with_urls() {
        InstallationService installationService = new FileInstallationService(new AppConfig(), "target/files");
        OAuthDefaultSuccessHandler handler = new OAuthDefaultSuccessHandler(new AppConfig(), installationService);

        RequestHeaders headers = new RequestHeaders(new HashMap<>());
        OAuthCallbackRequest request = new OAuthCallbackRequest(new HashMap<>(), "", VerificationCodePayload.from(new HashMap<>()), headers);
        request.getContext().setOauthCompletionUrl("https://www.example.com/completion");
        request.getContext().setOauthCancellationUrl("https://www.example.com/cancellation");

        Response response = new Response();

        OAuthAccessResponse apiResponse = new OAuthAccessResponse();

        Response processedResponse = handler.handle(request, response, apiResponse);
        assertEquals(302, processedResponse.getStatusCode().longValue());
        assertEquals("https://www.example.com/completion", processedResponse.getHeaders().get("Location").get(0));
    }

    @Test
    public void failure() {
        InstallationService installationService = new FileInstallationService(new AppConfig(), "target/files") {
            @Override
            public void saveInstallerAndBot(Installer installer) {
                throw new RuntimeException("something_wrong");
            }
        };
        OAuthDefaultSuccessHandler handler = new OAuthDefaultSuccessHandler(new AppConfig(), installationService);

        RequestHeaders headers = new RequestHeaders(new HashMap<>());
        OAuthCallbackRequest request = new OAuthCallbackRequest(new HashMap<>(), "", VerificationCodePayload.from(new HashMap<>()), headers);

        Response response = new Response();

        OAuthAccessResponse apiResponse = new OAuthAccessResponse();

        Response processedResponse = handler.handle(request, response, apiResponse);
        assertEquals(200, processedResponse.getStatusCode().longValue());
        assertEquals("text/html; charset=utf-8", processedResponse.getContentType());
        assertEquals("<html>\n" +
                "<head>\n" +
                "<style>\n" +
                "body {\n" +
                "  padding: 10px 15px;\n" +
                "  font-family: verdana;\n" +
                "  text-align: center;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h2>Oops, Something Went Wrong!</h2>\n" +
                "<p>Please try again from <a href=\"start\">here</a> or contact the app owner (reason: something_wrong)</p>\n" +
                "</body>\n" +
                "</html>", processedResponse.getBody());
    }

    @Test
    public void failure_with_urls() {
        InstallationService installationService = new FileInstallationService(new AppConfig(), "target/files") {
            @Override
            public void saveInstallerAndBot(Installer installer) {
                throw new RuntimeException();
            }
        };
        OAuthDefaultSuccessHandler handler = new OAuthDefaultSuccessHandler(new AppConfig(), installationService);

        RequestHeaders headers = new RequestHeaders(new HashMap<>());
        OAuthCallbackRequest request = new OAuthCallbackRequest(new HashMap<>(), "", VerificationCodePayload.from(new HashMap<>()), headers);
        request.getContext().setOauthCompletionUrl("https://www.example.com/completion");
        request.getContext().setOauthCancellationUrl("https://www.example.com/cancellation");

        Response response = new Response();

        OAuthAccessResponse apiResponse = new OAuthAccessResponse();

        Response processedResponse = handler.handle(request, response, apiResponse);
        assertEquals(302, processedResponse.getStatusCode().longValue());
        assertEquals("https://www.example.com/cancellation", processedResponse.getHeaders().get("Location").get(0));
    }
}
