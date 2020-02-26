package test_locally;

import com.slack.api.meta.SlackApiModelLibraryVersion;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class SlackApiModelLibraryVersionTest {

    @Test
    public void test() {
        assertNotNull(SlackApiModelLibraryVersion.get());
    }
}
