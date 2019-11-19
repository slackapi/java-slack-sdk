package test_locally.app_backend.vendor.aws.lambda.request;

import com.github.seratch.jslack.app_backend.vendor.aws.lambda.request.ApiGatewayRequest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ApiGatewayRequestTest {

    @Test
    public void test() {
        ApiGatewayRequest req = new ApiGatewayRequest();
        req.setBody("something");
        assertThat(req.getBody(), is("something"));
    }

}
