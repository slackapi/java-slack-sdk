package test_locally.app_backend.vendor.aws.lambda.response;

import com.github.seratch.jslack.app_backend.vendor.aws.lambda.response.ApiGatewayResponse;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ApiGatewayResponseTest {

    @Test
    public void test() {
        ApiGatewayResponse response = ApiGatewayResponse.builder().rawBody("something").build();
        assertThat(response.getBody(), is("something"));
    }

}
