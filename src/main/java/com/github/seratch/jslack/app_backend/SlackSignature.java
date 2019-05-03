package com.github.seratch.jslack.app_backend;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * see "https://api.slack.com/docs/verifying-requests-from-slack"
 */
public class SlackSignature {

    public static final String ALGORITHM = "HmacSHA256";


    private SlackSignature() {
    }

    public static class HeaderNames {
        private HeaderNames() {
        }

        public static final String X_SLACK_REQUEST_TIMESTAMP = "X-Slack-Request-Timestamp";

        public static final String X_SLACK_SIGNATURE = "X-Slack-Signature";

    }

    public static class Secret {
        private Secret() {
        }

        public static final String DEFAULT_ENV_NAME = "SLACK_SIGNING_SECRET";
    }


    @Slf4j
    public static class Generator {

        private final String slackSigningSecret;

        public Generator() {
            this(System.getenv(Secret.DEFAULT_ENV_NAME));
        }

        public Generator(String slackSigningSecret) {
            this.slackSigningSecret = slackSigningSecret;
        }

        public String generate(String slackRequestTimestamp, String requestBody) {
            if (slackRequestTimestamp == null) {
                return null;
            }

            // 1) Retrieve the X-Slack-Request-Timestamp header on the HTTP request, and the body of the request.
            // "slackRequestTimestamp" here

            // 2) Concatenate the version number, the timestamp, and the body of the request to form a basestring.
            //    Use a colon as the delimiter between the three elements.
            //    For example, v0:123456789:command=/weather&text=94070. The version number right now is always v0.
            String baseString = "v0:" + slackRequestTimestamp + ":" + requestBody;

            // 3) With the help of HMAC SHA256 implemented in your favorite programming, hash the above basestring,
            //    using the Slack Signing Secret as the key.
            SecretKeySpec sk = new SecretKeySpec(slackSigningSecret.getBytes(), ALGORITHM);
            try {
                Mac mac = Mac.getInstance(ALGORITHM);
                mac.init(sk);
                byte[] macBytes = mac.doFinal(baseString.getBytes());
                StringBuilder hashValue = new StringBuilder(2 * macBytes.length);
                for (byte macByte : macBytes) {
                    hashValue.append(String.format("%02x", macByte & 0xff));
                }
                return "v0=" + hashValue.toString();

                // 4) Compare this computed signature to the X-Slack-Signature header on the request.

            } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                log.error("Failed to hash the base string value with HMAC-SHA256 because {}", e.getMessage(), e);
                return null;
            }
        }

    }

}
