package com.github.seratch.jslack.app_backend.vendor.aws.lambda.request;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ApiGatewayRequestTest {

    @Test
    public void test() {
        ApiGatewayRequest req = new ApiGatewayRequest();
        req.setBody("something");
        assertThat(req.getBody(), is("something"));
    }

}