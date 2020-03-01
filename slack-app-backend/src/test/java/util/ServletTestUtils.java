package util;

import com.slack.api.app_backend.SlackSignature;
import org.eclipse.jetty.http.HttpTester;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletTester;

import javax.servlet.Servlet;

public class ServletTestUtils {

    private ServletTestUtils() {
    }

    public static ServletTester getServletTester(Servlet servlet) throws Exception {
        ServletTester tester = new ServletTester();
        tester.addServlet(new ServletHolder(servlet), "/");
        tester.start();
        return tester;
    }

    public static HttpTester.Request prepareRequest(String signingSecret, String requestBody) {
        HttpTester.Request request = HttpTester.newRequest();
        request.setMethod("POST");
        request.setHeader("Host", "tester"); // must be "tester"
        request.setURI("/");
        request.setVersion("HTTP/1.1");
        request.setHeader("content-type", "application/json");
        request.setHeader("Connection", "close");
        String timestamp = String.valueOf((System.currentTimeMillis() / 1000));
        SlackSignature.Generator signatureGenerator = new SlackSignature.Generator(signingSecret);
        String signature = signatureGenerator.generate(timestamp, requestBody);
        request.setHeader(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, timestamp);
        request.setHeader(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, signature);
        request.setContent(requestBody);
        return request;
    }
}
