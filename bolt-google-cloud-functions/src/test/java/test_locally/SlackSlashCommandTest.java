/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package test_locally;

import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.gson.Gson;
import com.slack.api.app_backend.SlackSignature;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.google_cloud_functions.SlackApiFunction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class SlackSlashCommandTest {

    static App app = new App(AppConfig.builder()
            .signingSecret("test-secret")
            .singleTeamBotToken("xoxb-valid")
            .build());

    static {
        app.command("/kg", (req, ctx) -> {
            return ctx.ack();
        });
    }

    static SlackApiFunction function = new SlackApiFunction(app);

    private BufferedWriter writerOut;
    private StringWriter responseOut;

    private static final Gson gson = new Gson();

    @Mock
    private HttpRequest request;
    @Mock
    private HttpResponse response;

    @Mock
    private SlackSignature.Verifier alwaysValidVerifier;

    @Before
    public void beforeTest() throws IOException {
        MockitoAnnotations.openMocks(this);

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("")));

        responseOut = new StringWriter();

        writerOut = new BufferedWriter(responseOut);
        when(response.getWriter()).thenReturn(writerOut);

        // Construct valid header list
        String validSlackSignature = "test-secret";
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);

        Map<String, List<String>> validHeaders = new HashMap<>();
        validHeaders.put("X-Slack-Signature", Arrays.asList(validSlackSignature));
        validHeaders.put("X-Slack-Request-Timestamp", Arrays.asList(timestamp));

        when(request.getHeaders()).thenReturn(validHeaders);
        when(request.getFirstHeader(any())).thenCallRealMethod();
    }

    @Test
    public void onlyAcceptsPostRequestsTest() throws Exception {
        when(request.getMethod()).thenReturn("GET");
        function.service(request, response);

        writerOut.flush();
        verify(response, times(1)).setStatusCode(HttpURLConnection.HTTP_BAD_METHOD);
    }

    @Test
    public void requiresSlackAuthHeadersTest() throws Exception {
        Map<String, String> value = new HashMap<>();
        value.put("text", "foo");
        String jsonStr = gson.toJson(value);
        StringReader requestReadable = new StringReader(jsonStr);

        when(request.getMethod()).thenReturn("POST");
        when(request.getReader()).thenReturn(new BufferedReader(requestReadable));

        function.service(request, response);

        // Do NOT look for HTTP_BAD_REQUEST here (that means the request WAS authorized)!
        verify(response, times(1)).setStatusCode(HttpURLConnection.HTTP_UNAUTHORIZED);
    }

    @Test
    public void recognizesValidSlackTokenTest() throws Exception {
        StringReader requestReadable = new StringReader("{}");

        when(request.getReader()).thenReturn(new BufferedReader(requestReadable));
        when(request.getMethod()).thenReturn("POST");

        function.service(request, response);

        verify(response, times(1)).setStatusCode(HttpURLConnection.HTTP_BAD_REQUEST);
    }

    @Test
    public void handlesEmptyKgResultsTest() throws Exception {
        Map<String, String> value = new HashMap<>();
        value.put("text", "asdfjkl13579");
        String jsonStr = gson.toJson(value);
        StringReader requestReadable = new StringReader(jsonStr);

        when(request.getReader()).thenReturn(new BufferedReader(requestReadable));
        when(request.getMethod()).thenReturn("POST");

        function.service(request, response);

        writerOut.flush();
        assertTrue(responseOut.toString().contains("No results match your query..."));
    }

    @Test
    public void handlesPopulatedKgResultsTest() throws Exception {
        Map<String, String> value = new HashMap<>();
        value.put("text", "lion");
        String jsonStr = gson.toJson(value);
        StringReader requestReadable = new StringReader(jsonStr);

        when(request.getReader()).thenReturn(new BufferedReader(requestReadable));
        when(request.getMethod()).thenReturn("POST");

        function.service(request, response);

        writerOut.flush();
        assertTrue(responseOut.toString().contains("https://en.wikipedia.org/wiki/Lion"));
    }
}