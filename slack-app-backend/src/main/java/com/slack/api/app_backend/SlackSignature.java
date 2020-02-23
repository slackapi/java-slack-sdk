package com.slack.api.app_backend;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Slack request signature representation in Java.
 *
 * @see <a href="https://api.slack.com/docs/verifying-requests-from-slack">Slack Request Verification</a>
 */
public class SlackSignature {

    public static final String ALGORITHM = "HmacSHA256";

    private SlackSignature() {
    }

    /**
     * The collection of the request header names related to the Slack request verification.
     */
    public static class HeaderNames {
        private HeaderNames() {
        }

        public static final String X_SLACK_REQUEST_TIMESTAMP = "X-Slack-Request-Timestamp";

        public static final String X_SLACK_SIGNATURE = "X-Slack-Signature";

    }

    /**
     * Secret key for the Slack request verification. The value is shared between Slack API server and a Slack app.
     */
    public static class Secret {
        private Secret() {
        }

        public static final String DEFAULT_ENV_NAME = "SLACK_SIGNING_SECRET";
    }

    /**
     * This utility verifies if the timestamp header value if valid.
     */
    public static class TimestampVerifier {

        public static final int TIMESTAMP_EXPIRATION_TIME_IN_MILLIS = 60 * 5 * 1000; // 5 minutes

        private TimestampVerifier() {
        }

        public static boolean isValidTimestamp(String timestampValue, long nowInMillis) {
            try {
                long requestMillis = Long.parseLong(timestampValue) * 1000;
                return (nowInMillis - requestMillis) < TIMESTAMP_EXPIRATION_TIME_IN_MILLIS;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        public static boolean isValidTimestamp(String timestampValue) {
            return isValidTimestamp(timestampValue, System.currentTimeMillis());
        }
    }

    /**
     * This utility has the ability to generate the Slack request signature.
     */
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

    /**
     * This utility has the ability to verify if a given signature and related values are valid.
     */
    @Slf4j
    public static class Verifier {

        private Generator signatureGenerator;

        public Verifier(Generator signatureGenerator) {
            this.signatureGenerator = signatureGenerator;
        }

        public boolean isValid(
                String requestTimestamp,
                String requestBody,
                String requestSignature) {
            return isValid(requestTimestamp, requestBody, requestSignature, System.currentTimeMillis());
        }

        public boolean isValid(
                String requestTimestamp,
                String requestBody,
                String requestSignature,
                long nowInMillis) {

            if (log.isDebugEnabled()) {
                log.debug("Request verification - secret: {}, timestamp: {}, body: {}, signature: {}",
                        signatureGenerator.slackSigningSecret,
                        requestTimestamp,
                        requestBody,
                        requestSignature
                );
            }

            if (signatureGenerator == null) {
                throw new IllegalStateException("SlackSignature.Generator is required");
            }
            if (requestTimestamp == null || requestSignature == null) {
                return false;
            }
            if (SlackSignature.TimestampVerifier.isValidTimestamp(requestTimestamp, nowInMillis)) {
                String expected = signatureGenerator.generate(requestTimestamp, requestBody);
                return requestSignature != null && expected != null && requestSignature.equals(expected);
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("The given X-Slack-Request-Timestamp value is expired - {}", requestTimestamp);
                }
                return false;
            }
        }
    }

}
