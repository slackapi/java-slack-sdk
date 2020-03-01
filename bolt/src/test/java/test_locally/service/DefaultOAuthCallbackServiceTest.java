package test_locally.service;

import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.builtin.OAuthCallbackRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.bolt.service.OAuthStateService;
import com.slack.api.bolt.service.builtin.ClientOnlyOAuthStateService;
import com.slack.api.bolt.service.builtin.DefaultOAuthCallbackService;
import com.slack.api.bolt.service.builtin.FileInstallationService;
import com.slack.api.bolt.service.builtin.oauth.*;
import com.slack.api.bolt.service.builtin.oauth.default_impl.*;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class DefaultOAuthCallbackServiceTest {

    @Test
    public void test() {
        AppConfig config = new AppConfig();
        InstallationService installationService = new FileInstallationService(AppConfig.builder().build(), "target/files");
        OAuthStateService stateService = new ClientOnlyOAuthStateService();
        OAuthSuccessHandler successHandler = new OAuthDefaultSuccessHandler(installationService);
        OAuthV2SuccessHandler successV2Handler = new OAuthV2DefaultSuccessHandler(installationService);
        OAuthErrorHandler errorHandler = new OAuthDefaultErrorHandler();
        OAuthStateErrorHandler stateErrorHandler = new OAuthDefaultStateErrorHandler();
        OAuthAccessErrorHandler accessErrorHandler = new OAuthDefaultAccessErrorHandler();
        OAuthV2AccessErrorHandler accessV2ErrorHandler = new OAuthV2DefaultAccessErrorHandler();
        OAuthExceptionHandler exceptionHandler = new OAuthDefaultExceptionHandler();
        DefaultOAuthCallbackService service = new DefaultOAuthCallbackService(
                config,
                stateService,
                successHandler,
                successV2Handler,
                errorHandler,
                stateErrorHandler,
                accessErrorHandler,
                accessV2ErrorHandler,
                exceptionHandler
        );
        Response response = service.handle(new OAuthCallbackRequest(null, null, null, null));
        assertNotNull(response);
    }
}
