package test_locally.api.util.http;

import com.slack.api.util.http.ProxyUrlUtil;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class ProxyUrlUtilTest {

    @Test
    public void toUrlWithoutUserAndPassword() {
        ProxyUrlUtil.ProxyUrl url = ProxyUrlUtil.ProxyUrl.builder()
                .schema("http://")
                .host("localhost")
                .username("user")
                .password("password")
                .port(9000)
                .build();
        assertThat(url.toUrlWithoutUserAndPassword(), is("http://localhost:9000"));
    }

    @Test
    public void parse_null() {
        assertThat(ProxyUrlUtil.parse(null), is(nullValue()));
    }

    @Test
    public void parse_no_username_password() {
        ProxyUrlUtil.ProxyUrl expected = ProxyUrlUtil.ProxyUrl.builder()
                .schema("http://")
                .host("localhost")
                .port(9000)
                .build();
        assertThat(ProxyUrlUtil.parse("http://localhost:9000"), is(expected));
    }

    @Test
    public void parse_username_password() {
        ProxyUrlUtil.ProxyUrl expected = ProxyUrlUtil.ProxyUrl.builder()
                .schema("http://")
                .host("localhost")
                .username("user")
                .password("password")
                .port(9000)
                .build();
        assertThat(ProxyUrlUtil.parse("http://user:password@localhost:9000"), is(expected));
    }

    @Test(expected = IllegalArgumentException.class)
    public void parse_invalid_format() {
        ProxyUrlUtil.parse("http://foo:bar:baz@localhost:9000");
    }

    @Test
    public void parse_no_schema() {
        ProxyUrlUtil.ProxyUrl expected = ProxyUrlUtil.ProxyUrl.builder()
                .schema("http://")
                .host("localhost")
                .port(9000)
                .build();
        assertThat(ProxyUrlUtil.parse("localhost:9000"), is(expected));
    }

    @Test
    public void parse_username_password_no_schema() {
        ProxyUrlUtil.ProxyUrl expected = ProxyUrlUtil.ProxyUrl.builder()
                .schema("http://")
                .host("localhost")
                .username("user")
                .password("password")
                .port(9000)
                .build();
        assertThat(ProxyUrlUtil.parse("user:password@localhost:9000"), is(expected));
    }
}
