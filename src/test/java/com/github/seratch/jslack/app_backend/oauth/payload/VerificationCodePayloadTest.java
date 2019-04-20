package com.github.seratch.jslack.app_backend.oauth.payload;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class VerificationCodePayloadTest {

    @Test
    public void test() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("code", "123");
        queryParameters.put("state", "random");
        VerificationCodePayload payload = VerificationCodePayload.from(queryParameters);
        assertThat(payload.getCode(), is("123"));
        assertThat(payload.getState(), is("random"));
    }

}