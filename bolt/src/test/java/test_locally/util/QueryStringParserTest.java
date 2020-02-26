package test_locally.util;

import com.slack.api.bolt.util.QueryStringParser;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class QueryStringParserTest {

    @Test
    public void test() {
        Map<String, List<String>> query = QueryStringParser.toMap("foo=bar&baz=123");
        assertEquals(query.get("foo").get(0), "bar");
        assertEquals(query.get("baz").get(0), "123");
        assertNull(query.get("token"));
    }
}
