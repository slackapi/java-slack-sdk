package test_locally.api.util.http;

import com.slack.api.util.http.RetryAfterUtil;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class RetryAfterUtilTest {

    @Test
    public void integerSeconds() {
        assertThat(RetryAfterUtil.parseSeconds("0"), is(0L));
        assertThat(RetryAfterUtil.parseSeconds("30"), is(30L));
    }

    @Test
    public void missing() {
        assertThat(RetryAfterUtil.parseSeconds(null), is(nullValue()));
    }

    @Test
    public void notAnInteger() {
        assertThat(RetryAfterUtil.parseSeconds(""), is(nullValue()));
        assertThat(RetryAfterUtil.parseSeconds("notanumber"), is(nullValue()));
        assertThat(RetryAfterUtil.parseSeconds("1.5"), is(nullValue()));
        assertThat(RetryAfterUtil.parseSeconds("Wed, 21 Oct 2026 07:28:00 GMT"), is(nullValue()));
        assertThat(RetryAfterUtil.parseSeconds("99999999999999999999"), is(nullValue()));
    }
}
