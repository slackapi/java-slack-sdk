package test_locally.app_backend.vendor.aws.lambda.response;

import com.slack.api.app_backend.vendor.aws.lambda.response.ApiGatewayResponse;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ApiGatewayResponseTest {

    @Test
    public void instantiation() {
        ApiGatewayResponse response = new ApiGatewayResponse(200, "something", null, false);
        assertThat(response.getBody(), is("something"));
    }

    @Test
    public void builder() {
        ApiGatewayResponse response = ApiGatewayResponse.builder().rawBody("something").build();
        assertThat(response.getBody(), is("something"));
    }

    @Test
    public void build302Response() {
        ApiGatewayResponse response = ApiGatewayResponse.build302Response("https://www.example.com/");
        assertThat(response.getStatusCode(), is(302));
        assertThat(response.getHeaders().get("Location"), is("https://www.example.com/"));
    }

}
