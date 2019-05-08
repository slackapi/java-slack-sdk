package com.github.seratch.jslack.app_backend.vendor.aws.lambda.response;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApiGatewayResponseTest {

    @Test
    public void test() {
        ApiGatewayResponse response = ApiGatewayResponse.builder().rawBody("something").build();
        assertThat(response.getBody(), is("something"));
    }

}