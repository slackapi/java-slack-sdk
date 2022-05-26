package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.response.openid.connect.OpenIDConnectTokenResponse;
import com.slack.api.methods.response.openid.connect.OpenIDConnectUserInfoResponse;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import util.ObjectInitializer;
import util.sample_json_generation.ObjectToJsonDumper;

import java.io.IOException;

@Slf4j
public class openid_connect_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @BeforeClass
    public static void setUp() throws Exception {
        SlackTestConfig.initializeRawJSONDataFiles("openid.connect.*");
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    @Test
    public void token_dump() throws IOException {
        ObjectToJsonDumper dumper = new ObjectToJsonDumper("../json-logs/samples/api");
        OpenIDConnectTokenResponse response = new OpenIDConnectTokenResponse();
        ObjectInitializer.initProperties(response);
        dumper.dump("openid.connect.token", response);
    }

    @Test
    public void userInfo_dump() throws IOException {
        ObjectToJsonDumper dumper = new ObjectToJsonDumper("../json-logs/samples/api");
        OpenIDConnectUserInfoResponse response = new OpenIDConnectUserInfoResponse();
        ObjectInitializer.initProperties(response);
        dumper.dump("openid.connect.userInfo", response);
    }

}
