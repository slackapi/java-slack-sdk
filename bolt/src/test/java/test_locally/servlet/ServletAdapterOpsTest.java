package test_locally.servlet;

import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.servlet.ServletAdapterOps;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class ServletAdapterOpsTest {

    @Test
    public void writeResponse() throws IOException {
        HttpServletResponse httpResponse = mock(HttpServletResponse.class);
        PrintWriter writer = mock(PrintWriter.class);
        when(httpResponse.getWriter()).thenReturn(writer);
        Response boltResponse = new Response();
        boltResponse.setStatusCode(404);
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Set-Cookie", Arrays.asList("foo=bar"));
        boltResponse.setHeaders(headers);
        boltResponse.setBody("This is a message for you!");

        ServletAdapterOps.writeResponse(httpResponse, boltResponse);

        verify(httpResponse, times(1)).setStatus(404);
        verify(httpResponse, times(1)).addHeader("Set-Cookie", "foo=bar");
        verify(httpResponse, times(1)).getWriter();
        verify(writer, times(1)).write("This is a message for you!");
    }
}
