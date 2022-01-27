package samples;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.slack.api.Slack;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.openid.connect.OpenIDConnectTokenResponse;
import com.slack.api.methods.response.openid.connect.OpenIDConnectUserInfoResponse;
import org.slf4j.Logger;
import util.ResourceLoader;
import util.TestSlackAppServer;

import java.util.HashMap;
import java.util.Map;

public class OpenIDConnectSample {

    public static void main(String[] args) throws Exception {
        AppConfig config = ResourceLoader.loadAppConfig("appConfig_OpenIDConnect.json");
        App app = new App(config).asOpenIDConnectApp(true);
        app.openIDConnectSuccess((req, resp, token) -> {
            Logger logger = req.getContext().getLogger();
            // Decode id_token in an openid.connect.token response
            DecodedJWT decoded = JWT.decode(token.getIdToken());
            Map<String, Claim> claims = decoded.getClaims();
            logger.info("claims: {}", claims);
            String teamId = claims.get("https://slack.com/team_id").asString();

            // Call openid.connect.userInfo using the given access token
            MethodsClient client = Slack.getInstance().methods();
            try {
                // If the token rotation is enabled
                if (token.getRefreshToken() != null) {
                    // When token rotation is enabled, auth.test API accepts only refresh_token
                    // AuthTestResponse authTest = client.authTest(r -> r.token(token.getRefreshToken()));
                    // String teamId = authTest.getTeamId() != null ? authTest.getTeamId() : authTest.getEnterpriseId();

                    // run the first token rotation
                    OpenIDConnectTokenResponse refreshedToken = client.openIDConnectToken(r -> r
                            .clientId(config.getClientId())
                            .clientSecret(config.getClientSecret())
                            .grantType("refresh_token")
                            .refreshToken(token.getRefreshToken())
                    );
                    MethodsClient teamIdWiredClient = Slack.getInstance().methods(refreshedToken.getAccessToken(), teamId);
                    OpenIDConnectUserInfoResponse userInfo = teamIdWiredClient.openIDConnectUserInfo(r -> r
                            .token(refreshedToken.getAccessToken()));
                    logger.info("userInfo: {}", userInfo);

                    // To revoke this, you can use auth.revoke API method
                    // Slack.getInstance().methods().authRevoke(r -> r.token(refreshedToken.getAccessToken()));

                } else {
                    OpenIDConnectUserInfoResponse userInfo = client.openIDConnectUserInfo(r -> r
                            .token(token.getAccessToken()));
                    logger.info("userInfo: {}", userInfo);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            String html = app.config().getOAuthRedirectUriPageRenderer().renderSuccessPage(
                    null, req.getContext().getOauthCompletionUrl());
            resp.setBody(html);
            resp.setContentType("text/html; charset=utf-8");
            return resp;
        });
        Map<String, App> apps = new HashMap<>();
        apps.put("/slack/", app);
        TestSlackAppServer server = new TestSlackAppServer(apps);
        server.start();
    }

}
