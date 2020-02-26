package test_locally.app_backend.outgoing_webhooks;

import com.slack.api.app_backend.outgoing_webhooks.WebhookPayloadDetector;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WebhookPayloadDetectorTest {

    @Test
    public void nullValue() {
        WebhookPayloadDetector detector = new WebhookPayloadDetector();
        boolean invalid = detector.isWebhook(null);
        assertFalse(invalid);
    }

    @Test
    public void empty() {
        WebhookPayloadDetector detector = new WebhookPayloadDetector();
        boolean invalid = detector.isWebhook("");
        assertFalse(invalid);
    }

    @Test
    public void valid() {
        WebhookPayloadDetector detector = new WebhookPayloadDetector();
        boolean valid = detector.isWebhook("trigger_word=something");
        assertTrue(valid);
    }
}
