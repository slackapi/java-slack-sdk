package functions;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.slack.api.bolt.App;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.util.SlackRequestParser;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.util.stream.Collectors.joining;

/**
 * An HttpFunction that handles incoming requests from Slack.
 */
public class SlackOAuthFunction implements HttpFunction {

    private final App app;
    private final SlackRequestParser requestParser;

    public SlackOAuthFunction(App app) {
        this.app = app;
        this.requestParser = new SlackRequestParser(this.app.config());
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        Request boltRequest = toBoltRequest(httpRequest);
        Response boltResponse = this.app.run(boltRequest);
        writeResponse(boltResponse, httpResponse);
    }

    private Request toBoltRequest(HttpRequest httpRequest) throws IOException {
        String body = httpRequest.getReader().lines().collect(joining());
        String requestUri = httpRequest.getPath();
        String method = httpRequest.getMethod().toLowerCase(Locale.ENGLISH);
        if (method.equals("get")) {
            // To handle OAuth flow by the same Cloud Function
            Map<String, List<String>> q = httpRequest.getQueryParameters();
            if (q.get("code") != null || q.get("error") != null) {
                requestUri = app.config().getOauthRedirectUriRequestURI();
            } else {
                requestUri = app.config().getOauthInstallRequestURI();
            }
        }
        SlackRequestParser.HttpRequest req = SlackRequestParser.HttpRequest.builder()
                .requestUri(requestUri)
                .queryString(httpRequest.getQueryParameters())
                .requestBody(body)
                .headers(new RequestHeaders(httpRequest.getHeaders()))
                .remoteAddress(httpRequest.getFirstHeader("X-Forwarded-For").orElse(null))
                .build();
        return this.requestParser.parse(req);
    }

    private void writeResponse(Response boltResponse, HttpResponse httpResponse) throws IOException {
        httpResponse.setStatusCode(boltResponse.getStatusCode());
        httpResponse.setContentType(boltResponse.getContentType());
        for (Map.Entry<String, List<String>> nameAndValues : boltResponse.getHeaders().entrySet()) {
            String headerName = nameAndValues.getKey();
            for (String value : nameAndValues.getValue()) {
                httpResponse.appendHeader(headerName, value);
            }
        }
        httpResponse.getWriter().write(boltResponse.getBody());
    }
}
