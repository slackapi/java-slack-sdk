package test_locally;

import com.google.cloud.functions.HttpResponse;
import com.slack.api.bolt.response.Response;
import org.junit.Test;

import java.io.*;
import java.util.*;

import static com.slack.api.bolt.google_cloud_functions.SlackApiFunction.buildNotNullResponseBody;
import static com.slack.api.bolt.google_cloud_functions.SlackApiFunction.writeResponse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ResponseUtilityTest {
    private static HttpResponse buildNullHttpResponse() {
        return new HttpResponse() {
            @Override
            public void setStatusCode(int code) {
            }

            @Override
            public void setStatusCode(int code, String message) {
            }

            @Override
            public void setContentType(String contentType) {
            }

            @Override
            public Optional<String> getContentType() {
                return Optional.empty();
            }

            @Override
            public void appendHeader(String header, String value) {
            }

            @Override
            public Map<String, List<String>> getHeaders() {
                return null;
            }

            @Override
            public OutputStream getOutputStream() throws IOException {
                return new ByteArrayOutputStream();
            }

            @Override
            public BufferedWriter getWriter() throws IOException {
                return new BufferedWriter(new StringWriter());
            }
        };
    }

    @Test
    public void writeResponse_null_headers() throws IOException {
        Response response = Response.builder().statusCode(200).body(null).headers(null).build();
        HttpResponse httpResponse = buildNullHttpResponse();
        writeResponse(response, httpResponse);
    }

    @Test
    public void writeResponse_non_null_values() throws IOException {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Set-Cookie", Arrays.asList("1", "2"));
        Response response = Response.builder().statusCode(200).body("foo").headers(headers).build();
        HttpResponse httpResponse = buildNullHttpResponse();
        writeResponse(response, httpResponse);
    }

    @Test
    public void toNotNullResponseBody_null() {
        Response response = Response.builder().statusCode(200).body(null).build();
        String textBody = buildNotNullResponseBody(response);
        assertNotNull(textBody);
    }

    @Test
    public void toNotNullResponseBody_some_value() {
        Response response = Response.builder().statusCode(200).body("ok").build();
        String textBody = buildNotNullResponseBody(response);
        assertEquals("ok", textBody);
    }
}
