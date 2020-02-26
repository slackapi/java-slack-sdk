package test_locally.app_backend.util;

import com.slack.api.app_backend.interactive_components.payload.AttachmentActionPayload;
import com.slack.api.app_backend.interactive_components.payload.BlockActionPayload;
import com.slack.api.app_backend.outgoing_webhooks.payload.WebhookPayload;
import com.slack.api.app_backend.util.RequestTokenVerifier;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class RequestTokenVerifierTest {

    @Test
    public void noArgConstructor() {
        RequestTokenVerifier verifier = new RequestTokenVerifier();
        assertNotNull(verifier);
    }

    @Test
    public void test() {
        RequestTokenVerifier verifier = new RequestTokenVerifier("foo");
        boolean valid = verifier.isValid("foo");
        assertTrue(valid);
    }

    @Test
    public void testWebhookPayload() {
        RequestTokenVerifier verifier = new RequestTokenVerifier("foo");
        WebhookPayload payload = new WebhookPayload();
        payload.setToken("foo");
        boolean valid = verifier.isValid(payload);
        assertTrue(valid);
    }

    @Test
    public void testAttachmentActionPayload() {
        RequestTokenVerifier verifier = new RequestTokenVerifier("foo");
        AttachmentActionPayload payload = new AttachmentActionPayload();
        payload.setToken("foo");
        boolean valid = verifier.isValid(payload);
        assertTrue(valid);
    }

    @Test
    public void testBlockActionPayload() {
        RequestTokenVerifier verifier = new RequestTokenVerifier("foo");
        BlockActionPayload payload = new BlockActionPayload();
        payload.setToken("foo");
        boolean valid = verifier.isValid(payload);
        assertTrue(valid);
    }
}
