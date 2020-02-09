package test_locally.app_backend.oauth.payload;

import com.slack.api.app_backend.oauth.payload.VerificationCodePayload;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class VerificationCodePayloadTest {

    @Test
    public void test() {
        Map<String, List<String>> queryParameters = new HashMap<>();
        queryParameters.put("code", Arrays.asList("123"));
        queryParameters.put("state", Arrays.asList("random"));
        VerificationCodePayload payload = VerificationCodePayload.from(queryParameters);
        assertThat(payload.getCode(), is("123"));
        assertThat(payload.getState(), is("random"));
        assertThat(payload.getError(), is(nullValue()));
    }

    @Test
    public void error() {
        Map<String, List<String>> queryParameters = new HashMap<>();
        queryParameters.put("error", Arrays.asList("access_denied"));
        VerificationCodePayload payload = VerificationCodePayload.from(queryParameters);
        assertThat(payload.getCode(), is(nullValue()));
        assertThat(payload.getState(), is(nullValue()));
        assertThat(payload.getError(), is("access_denied"));
    }

}
