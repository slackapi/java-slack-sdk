package test_locally.app_backend.vendor.aws.lambda.request;

import com.slack.api.app_backend.vendor.aws.lambda.request.ApiGatewayRequest;
import com.slack.api.app_backend.vendor.aws.lambda.request.PayloadExtractor;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PayloadExtractorTest {

    String body = "payload=%7B%22type%22%3A%22block_actions%22%2C%22team%22%3A%7B%22id%22%3A%22T00000000%22%2C%22domain%22%3A%22seratch%22%7D%2C%22user%22%3A%7B%22id%22%3A%22U00000000%22%2C%22username%22%3A%22seratch%22%2C%22name%22%3A%22seratch%22%2C%22team_id%22%3A%22T00000000%22%7D%2C%22api_app_id%22%3A%22A00000000%22%2C%22token%22%3A%22XXXXXXXXXXXXXXXXXXXXX%22%2C%22container%22%3A%7B%22type%22%3A%22message%22%2C%22message_ts%22%3A%221556590015.000400%22%2C%22channel_id%22%3A%22C00000000%22%2C%22is_ephemeral%22%3Atrue%7D%2C%22trigger_id%22%3A%22621444993508.3485157640.a5a968b1b0614dc5396045aefdd57a55%22%2C%22channel%22%3A%7B%22id%22%3A%22C00000000%22%2C%22name%22%3A%22general%22%7D%2C%22response_url%22%3A%22https%3A%5C%2F%5C%2Fhooks.slack.com%5C%2Factions%5C%2FT00000000%5C%2F1111111111111%5C%2Fxxxxxxxx%22%2C%22actions%22%3A%5B%7B%22action_id%22%3A%22Q5joJ%22%2C%22block_id%22%3A%22z9X%22%2C%22text%22%3A%7B%22type%22%3A%22plain_text%22%2C%22text%22%3A%22Show+more%22%2C%22emoji%22%3Atrue%7D%2C%22value%22%3A%22xxx%22%2C%22type%22%3A%22button%22%2C%22action_ts%22%3A%221556590017.241796%22%7D%5D%7D";

    String expected = "{\"type\":\"block_actions\"," +
            "\"team\":{\"id\":\"T00000000\",\"domain\":\"seratch\"}," +
            "\"user\":{\"id\":\"U00000000\",\"username\":\"seratch\",\"name\":\"seratch\",\"team_id\":\"T00000000\"}," +
            "\"api_app_id\":\"A00000000\"," +
            "\"token\":\"XXXXXXXXXXXXXXXXXXXXX\"," +
            "\"container\":{\"type\":\"message\",\"message_ts\":\"1556590015.000400\",\"channel_id\":\"C00000000\",\"is_ephemeral\":true}," +
            "\"trigger_id\":\"621444993508.3485157640.a5a968b1b0614dc5396045aefdd57a55\"," +
            "\"channel\":{\"id\":\"C00000000\",\"name\":\"general\"}," +
            "\"response_url\":\"https:\\/\\/hooks.slack.com\\/actions\\/T00000000\\/1111111111111\\/xxxxxxxx\"," +
            "\"actions\":[{\"action_id\":\"Q5joJ\",\"block_id\":\"z9X\",\"text\":{\"type\":\"plain_text\",\"text\":\"Show more\",\"emoji\":true},\"value\":\"xxx\",\"type\":\"button\",\"action_ts\":\"1556590017.241796\"}]" +
            "}";

    @Test
    public void extractFromBody() {
        String json = new PayloadExtractor().extractPayloadJsonAsString(body);
        assertThat(json, is(expected));
    }

    @Test
    public void extractFromRequest() {
        ApiGatewayRequest request = new ApiGatewayRequest();
        request.setBody(body);
        String json = new PayloadExtractor().extractPayloadJsonAsString(request);
        assertThat(json, is(expected));
    }

}
