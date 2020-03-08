package test_locally.middleware;

import com.slack.api.bolt.middleware.MiddlewareOps;
import com.slack.api.bolt.request.RequestType;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MiddlewareOpsTest {

    @Test
    public void needAuth() {
        assertFalse(MiddlewareOps.isNoAuthRequiredRequest(RequestType.ViewSubmission));
        assertFalse(MiddlewareOps.isNoAuthRequiredRequest(RequestType.ViewClosed));
        assertFalse(MiddlewareOps.isNoAuthRequiredRequest(RequestType.BlockAction));
        assertFalse(MiddlewareOps.isNoAuthRequiredRequest(RequestType.BlockSuggestion));
        assertFalse(MiddlewareOps.isNoAuthRequiredRequest(RequestType.Command));
    }

    @Test
    public void doesNotNeedAuth() {
        assertTrue(MiddlewareOps.isNoAuthRequiredRequest(RequestType.SSLCheck));
        assertTrue(MiddlewareOps.isNoAuthRequiredRequest(RequestType.OAuthCallback));
        assertTrue(MiddlewareOps.isNoAuthRequiredRequest(RequestType.OAuthStart));
        assertTrue(MiddlewareOps.isNoAuthRequiredRequest(RequestType.UrlVerification));
    }

    @Test
    public void needSignatureVerification() {
        assertFalse(MiddlewareOps.isNoSlackSignatureRequest(RequestType.ViewSubmission));
        assertFalse(MiddlewareOps.isNoSlackSignatureRequest(RequestType.ViewClosed));
        assertFalse(MiddlewareOps.isNoSlackSignatureRequest(RequestType.BlockAction));
        assertFalse(MiddlewareOps.isNoSlackSignatureRequest(RequestType.BlockSuggestion));
        assertFalse(MiddlewareOps.isNoSlackSignatureRequest(RequestType.Command));
    }

    @Test
    public void doesNotNeedSignatureVerification() {
        assertTrue(MiddlewareOps.isNoSlackSignatureRequest(RequestType.SSLCheck));
        assertTrue(MiddlewareOps.isNoSlackSignatureRequest(RequestType.OAuthCallback));
        assertTrue(MiddlewareOps.isNoSlackSignatureRequest(RequestType.OAuthStart));
    }

}
