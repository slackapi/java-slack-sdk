package test_locally;

import com.slack.api.SlackConfig;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class SlackConfigTest {

    @Test
    public void instantiation() {
        SlackConfig config = new SlackConfig();
        assertThat(config, is(notNullValue()));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void immutableDefaultConfig_setPrettyResponseLoggingEnabled() {
        SlackConfig.DEFAULT.setPrettyResponseLoggingEnabled(false);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void immutableDefaultConfig_setLibraryMaintainerMode() {
        SlackConfig.DEFAULT.setLibraryMaintainerMode(false);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void immutableDefaultConfig_setFailOnUnknownProperties() {
        SlackConfig.DEFAULT.setFailOnUnknownProperties(false);
    }

}
