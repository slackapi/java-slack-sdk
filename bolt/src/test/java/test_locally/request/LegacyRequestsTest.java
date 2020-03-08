package test_locally.request;

import com.google.gson.Gson;
import com.slack.api.app_backend.dialogs.payload.DialogCancellationPayload;
import com.slack.api.app_backend.dialogs.payload.DialogSubmissionPayload;
import com.slack.api.app_backend.dialogs.payload.DialogSuggestionPayload;
import com.slack.api.app_backend.interactive_components.payload.AttachmentActionPayload;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.RequestType;
import com.slack.api.bolt.request.builtin.AttachmentActionRequest;
import com.slack.api.bolt.request.builtin.DialogCancellationRequest;
import com.slack.api.bolt.request.builtin.DialogSubmissionRequest;
import com.slack.api.bolt.request.builtin.DialogSuggestionRequest;
import com.slack.api.util.json.GsonFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LegacyRequestsTest {

    Gson gson = GsonFactory.createSnakeCase();
    RequestHeaders requestHeaders = new RequestHeaders(Collections.emptyMap());

    String attachmentActionPayloadString;
    AttachmentActionRequest attachmentActionRequest;

    String dialogCancellationPayloadString;
    DialogCancellationRequest dialogCancellationRequest;

    String dialogSuggestionPayloadString;
    DialogSuggestionRequest dialogSuggestionRequest;

    String dialogSubmissionPayloadString;
    DialogSubmissionRequest dialogSubmissionRequest;

    @Before
    public void setup() throws UnsupportedEncodingException {
        AttachmentActionPayload attachmentActionPayload = new AttachmentActionPayload();
        attachmentActionPayload.setTeam(new AttachmentActionPayload.Team());
        attachmentActionPayload.setUser(new AttachmentActionPayload.User());
        attachmentActionPayload.setChannel(new AttachmentActionPayload.Channel());
        attachmentActionPayloadString = gson.toJson(attachmentActionPayload);
        attachmentActionRequest = new AttachmentActionRequest(
                "payload=" + URLEncoder.encode(attachmentActionPayloadString, "UTF-8"),
                attachmentActionPayloadString,
                requestHeaders
        );

        DialogCancellationPayload dialogCancellationPayload = new DialogCancellationPayload();
        dialogCancellationPayload.setTeam(new DialogCancellationPayload.Team());
        dialogCancellationPayload.setUser(new DialogCancellationPayload.User());
        dialogCancellationPayload.setChannel(new DialogCancellationPayload.Channel());
        dialogCancellationPayloadString = gson.toJson(dialogCancellationPayload);
        dialogCancellationRequest = new DialogCancellationRequest(
                "payload=" + URLEncoder.encode(dialogCancellationPayloadString, "UTF-8"),
                dialogCancellationPayloadString,
                requestHeaders
        );

        DialogSuggestionPayload dialogSuggestionPayload = new DialogSuggestionPayload();
        dialogSuggestionPayload.setTeam(new DialogSuggestionPayload.Team());
        dialogSuggestionPayload.setUser(new DialogSuggestionPayload.User());
        dialogSuggestionPayload.setChannel(new DialogSuggestionPayload.Channel());
        dialogSuggestionPayloadString = gson.toJson(dialogSuggestionPayload);
        dialogSuggestionRequest = new DialogSuggestionRequest(
                "payload=" + URLEncoder.encode(dialogSuggestionPayloadString, "UTF-8"),
                dialogSuggestionPayloadString,
                requestHeaders
        );

        DialogSubmissionPayload dialogSubmissionPayload = new DialogSubmissionPayload();
        dialogSubmissionPayload.setTeam(new DialogSubmissionPayload.Team());
        dialogSubmissionPayload.setUser(new DialogSubmissionPayload.User());
        dialogSubmissionPayload.setChannel(new DialogSubmissionPayload.Channel());
        dialogSubmissionPayloadString = gson.toJson(dialogSuggestionPayload);
        dialogSubmissionRequest = new DialogSubmissionRequest(
                "payload=" + URLEncoder.encode(dialogSubmissionPayloadString, "UTF-8"),
                dialogSubmissionPayloadString,
                requestHeaders
        );
    }

    @Test
    public void getRequestType() {
        assertEquals(RequestType.AttachmentAction, attachmentActionRequest.getRequestType());
        assertEquals(RequestType.DialogCancellation, dialogCancellationRequest.getRequestType());
        assertEquals(RequestType.DialogSuggestion, dialogSuggestionRequest.getRequestType());
        assertEquals(RequestType.DialogSubmission, dialogSubmissionRequest.getRequestType());
    }

    @Test
    public void getRequestBodyAsString() throws UnsupportedEncodingException {
        assertEquals(
                "payload=" + URLEncoder.encode(attachmentActionPayloadString, "UTF-8"),
                attachmentActionRequest.getRequestBodyAsString());
        assertEquals(
                "payload=" + URLEncoder.encode(dialogCancellationPayloadString, "UTF-8"),
                dialogCancellationRequest.getRequestBodyAsString());
        assertEquals(
                "payload=" + URLEncoder.encode(dialogSuggestionPayloadString, "UTF-8"),
                dialogSuggestionRequest.getRequestBodyAsString());
        assertEquals(
                "payload=" + URLEncoder.encode(dialogSubmissionPayloadString, "UTF-8"),
                dialogSubmissionRequest.getRequestBodyAsString());
    }

    @Test
    public void getHeaders() {
        assertEquals(requestHeaders, attachmentActionRequest.getHeaders());
        assertEquals(requestHeaders, dialogCancellationRequest.getHeaders());
        assertEquals(requestHeaders, dialogSuggestionRequest.getHeaders());
        assertEquals(requestHeaders, dialogSubmissionRequest.getHeaders());
    }

    @Test
    public void getPayload() {
        assertNotNull(attachmentActionRequest.getPayload());
        assertNotNull(dialogCancellationRequest.getPayload());
        assertNotNull(dialogSuggestionRequest.getPayload());
        assertNotNull(dialogSubmissionRequest.getPayload());
    }
}
