package test_locally.app_backend.oauth.payload;

import com.github.seratch.jslack.app_backend.oauth.payload.VerificationCodePayload;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class VerificationCodePayloadTest {

    @Test
    public void test() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("code", "123");
        queryParameters.put("state", "random");
        VerificationCodePayload payload = VerificationCodePayload.from(queryParameters);
        assertThat(payload.getCode(), is("123"));
        assertThat(payload.getState(), is("random"));
        assertThat(payload.getError(), is(nullValue()));
    }

    @Test
    public void error() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("error", "access_denied");
        VerificationCodePayload payload = VerificationCodePayload.from(queryParameters);
        assertThat(payload.getCode(), is(nullValue()));
        assertThat(payload.getState(), is(nullValue()));
        assertThat(payload.getError(), is("access_denied"));
    }

}