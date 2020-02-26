package test_locally.service.oauth;

import com.slack.api.app_backend.oauth.payload.VerificationCodePayload;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.model.Installer;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.OAuthCallbackRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.bolt.service.builtin.FileInstallationService;
import com.slack.api.bolt.service.builtin.oauth.default_impl.OAuthV2DefaultSuccessHandler;
import com.slack.api.methods.response.oauth.OAuthV2AccessResponse;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class OAuthV2DefaultSuccessHandlerTest {

    @Test
    public void completion() {
        InstallationService installationService = new FileInstallationService(new AppConfig(), "target/files");
        OAuthV2DefaultSuccessHandler handler = new OAuthV2DefaultSuccessHandler(installationService);

        RequestHeaders headers = new RequestHeaders(new HashMap<>());
        OAuthCallbackRequest request = new OAuthCallbackRequest(new HashMap<>(), "", VerificationCodePayload.from(new HashMap<>()), headers);
        request.getContext().setOauthCompletionUrl("https://www.example.com/completion");
        request.getContext().setOauthCancellationUrl("https://www.example.com/cancellation");

        Response response = new Response();

        OAuthV2AccessResponse apiResponse = new OAuthV2AccessResponse();
        apiResponse.setTeam(new OAuthV2AccessResponse.Team());
        apiResponse.setAuthedUser(new OAuthV2AccessResponse.AuthedUser());

        Response processedResponse = handler.handle(request, response, apiResponse);
        assertEquals(302, processedResponse.getStatusCode().longValue());
        assertEquals("https://www.example.com/completion", processedResponse.getHeaders().get("Location").get(0));
    }

    @Test
    public void failure() {
        InstallationService installationService = new FileInstallationService(new AppConfig(), "target/files") {
            @Override
            public void saveInstallerAndBot(Installer installer) {
                throw new RuntimeException();
            }
        };
        OAuthV2DefaultSuccessHandler handler = new OAuthV2DefaultSuccessHandler(installationService);

        RequestHeaders headers = new RequestHeaders(new HashMap<>());
        OAuthCallbackRequest request = new OAuthCallbackRequest(new HashMap<>(), "", VerificationCodePayload.from(new HashMap<>()), headers);
        request.getContext().setOauthCompletionUrl("https://www.example.com/completion");
        request.getContext().setOauthCancellationUrl("https://www.example.com/cancellation");

        Response response = new Response();

        OAuthV2AccessResponse apiResponse = new OAuthV2AccessResponse();
        apiResponse.setTeam(new OAuthV2AccessResponse.Team());
        apiResponse.setAuthedUser(new OAuthV2AccessResponse.AuthedUser());

        Response processedResponse = handler.handle(request, response, apiResponse);
        assertEquals(302, processedResponse.getStatusCode().longValue());
        assertEquals("https://www.example.com/cancellation", processedResponse.getHeaders().get("Location").get(0));
    }
}
