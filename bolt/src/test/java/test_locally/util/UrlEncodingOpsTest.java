package test_locally.util;

import org.junit.Test;

import static com.slack.api.bolt.util.UrlEncodingOps.urlEncode;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class UrlEncodingOpsTest {

    @Test
    public void testUrlEncode() {
        String result = urlEncode("foo,bar,baz");
        assertThat(result, is("foo%2Cbar%2Cbaz"));
    }

    @Test
    public void testUrlEncode_CJK() {
        String result = urlEncode("日本語");
        assertThat(result, is("%E6%97%A5%E6%9C%AC%E8%AA%9E"));
    }

    @Test
    public void testUrlEncode_null() {
        String result = urlEncode(null);
        assertThat(result, is(nullValue()));
    }

}
