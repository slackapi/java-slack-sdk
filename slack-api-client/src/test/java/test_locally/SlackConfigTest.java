package test_locally;

import com.slack.api.SlackConfig;
import com.slack.api.methods.MethodsConfig;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class SlackConfigTest {

    @Test
    public void instantiation() {
        SlackConfig config = new SlackConfig();
        assertThat(config, is(notNullValue()));
    }

    @Test
    public void immutableDefaultConfig() {
        try {
            SlackConfig.DEFAULT.setFailOnUnknownProperties(false);
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
        try {
            SlackConfig.DEFAULT.setPrettyResponseLoggingEnabled(false);
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
        try {
            SlackConfig.DEFAULT.setLibraryMaintainerMode(false);
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
        try {
            SlackConfig.DEFAULT.setTokenExistenceVerificationEnabled(false);
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
        try {
            SlackConfig.DEFAULT.setHttpClientResponseHandlers(Collections.emptyList());
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
        try {
            SlackConfig.DEFAULT.setAuditEndpointUrlPrefix("");
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
        try {
            SlackConfig.DEFAULT.setMethodsEndpointUrlPrefix("");
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
        try {
            SlackConfig.DEFAULT.setScimEndpointUrlPrefix("");
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
        try {
            SlackConfig.DEFAULT.setStatusEndpointUrlPrefix("");
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
        try {
            SlackConfig.DEFAULT.setLegacyStatusEndpointUrlPrefix("");
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
        try {
            SlackConfig.DEFAULT.setMethodsConfig(new MethodsConfig());
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
        try {
            SlackConfig.DEFAULT.setProxyUrl("");
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
        try {
            SlackConfig.DEFAULT.setAuditConfig(null);
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
        try {
            SlackConfig.DEFAULT.setSCIMConfig(null);
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
    }

}
