package test_locally.app_backend.dialogs.payload;

import com.slack.api.app_backend.dialogs.payload.PayloadTypeDetector;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PayloadTypeDetectorTest {

    @Test
    public void dialog_cancellation() {
        PayloadTypeDetector detector = new PayloadTypeDetector();
        String type = detector.detectType(DialogCancellationPayloadTest.JSON);
        assertThat(type, is("dialog_cancellation"));
    }

    @Test
    public void dialog_submission() {
        PayloadTypeDetector detector = new PayloadTypeDetector();
        String type = detector.detectType(DialogSubmissionPayloadTest.JSON);
        assertThat(type, is("dialog_submission"));
    }

    @Test
    public void dialog_suggestion() {
        PayloadTypeDetector detector = new PayloadTypeDetector();
        String type = detector.detectType(DialogSuggestionPayloadTest.JSON);
        assertThat(type, is("dialog_suggestion"));
    }

}
