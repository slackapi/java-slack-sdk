package test_with_remote_apis.commands;

import com.github.seratch.jslack.app_backend.SlackSignature;
import com.github.seratch.jslack.app_backend.events.servlet.SlackSignatureVerifier;
import com.github.seratch.jslack.app_backend.slash_commands.payload.SlashCommandPayload;
import com.github.seratch.jslack.app_backend.slash_commands.payload.SlashCommandPayloadParser;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class SlashCommandApiBackend {

    @Slf4j
    @WebServlet(urlPatterns = "/slack/events")
    public static class SlackEventsServlet extends HttpServlet {
        private final SlackSignatureVerifier signatureVerifier = new SlackSignatureVerifier();
        private final SlashCommandPayloadParser parser = new SlashCommandPayloadParser();

        protected String doReadRequestBodyAsString(HttpServletRequest req) throws IOException {
            return req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        }

        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            String requestBody = doReadRequestBodyAsString(req);
            boolean validSignature = this.signatureVerifier.isValid(req, requestBody);
            if (!validSignature) { // invalid signature
                if (log.isDebugEnabled()) {
                    String signature = req.getHeader(SlackSignature.HeaderNames.X_SLACK_SIGNATURE);
                    log.debug("An invalid X-Slack-Signature detected - {}", signature);
                }
                resp.setStatus(401);
                return;
            }

            SlashCommandPayload payload = parser.parse(requestBody);

            resp.setStatus(200);
            resp.setHeader("Content-Type", "text/plain");
            resp.getWriter().write(payload.getText());
        }
    }

    // https://www.eclipse.org/jetty/documentation/current/embedding-jetty.html

    public static void main(String[] args) throws Exception {
        Server server = new Server(3000);
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(SlackEventsServlet.class, "/*");
        server.start();
        server.join();
    }
}

