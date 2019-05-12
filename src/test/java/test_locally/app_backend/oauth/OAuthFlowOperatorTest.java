package test_locally.app_backend.oauth;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.response.oauth.OAuthAccessResponse;
import com.github.seratch.jslack.app_backend.config.SlackAppConfig;
import com.github.seratch.jslack.app_backend.oauth.OAuthFlowOperator;
import com.github.seratch.jslack.app_backend.oauth.payload.VerificationCodePayload;
import com.github.seratch.jslack.common.http.SlackHttpClient;
import okhttp3.*;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OAuthFlowOperatorTest {

    @Test
    public void callOAuthAccessMethod() throws IOException, SlackApiException {
        SlackHttpClient httpClient = mock(SlackHttpClient.class);
        Request httpRequest = mock(Request.class);
        Response httpResponse = mock(Response.class);
        ResponseBody httpResponseBody = mock(ResponseBody.class);
        RequestBody httpRequestBody = mock(RequestBody.class);
        when(httpClient.postForm(anyString(), any(FormBody.class))).thenReturn(httpResponse);
        when(httpResponse.code()).thenReturn(200);
        when(httpResponse.body()).thenReturn(httpResponseBody);
        when(httpResponseBody.string()).thenReturn("{\"ok\": true}");
        when(httpClient.parseJsonResponse(any(Response.class), eq(OAuthAccessResponse.class))).thenReturn(new OAuthAccessResponse());
        when(httpResponse.request()).thenReturn(httpRequest);
        when(httpRequest.body()).thenReturn(httpRequestBody);

        Slack slack = Slack.getInstance(httpClient);
        SlackAppConfig config = SlackAppConfig.builder().build();
        OAuthFlowOperator operator = new OAuthFlowOperator(slack, config);

        VerificationCodePayload payload = new VerificationCodePayload();
        payload.setCode("123");
        payload.setState("random");
        OAuthAccessResponse response = operator.callOAuthAccessMethod(payload);
        assertThat(response.getError(), is(nullValue()));
    }

}