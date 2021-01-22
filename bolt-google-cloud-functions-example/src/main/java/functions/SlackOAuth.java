package functions;

import com.google.cloud.storage.*;
import com.google.gson.Gson;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.model.Bot;
import com.slack.api.bolt.model.Installer;
import com.slack.api.bolt.model.builtin.DefaultBot;
import com.slack.api.bolt.model.builtin.DefaultInstaller;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.bolt.service.OAuthStateService;
import com.slack.api.util.json.GsonFactory;

import java.nio.charset.StandardCharsets;

// export SLACK_CLIENT_ID=
// export SLACK_CLIENT_SECRET=
// export SLACK_SCOPES=
// export SLACK_SIGNING_SECRET=
// export GOOGLE_CLOUD_PROJECT_ID=
// export GOOGLE_CLOUD_STORAGE_BUCKET_NAME=

// Local Development
// mvn function:run -Drun.functionTarget=functions.SlackOAuth
// ngrok http 8080 --subdomain your-domain

// Deployment
// gcloud functions deploy my-first-function --entry-point functions.SlackOAuth --runtime java11 --trigger-http --memory 512MB --allow-unauthenticated --set-env-vars ^/^"SLACK_CLIENT_ID=$SLACK_CLIENT_ID/SLACK_CLIENT_SECRET=$SLACK_CLIENT_SECRET/SLACK_SCOPES=$SLACK_SCOPES/SLACK_SIGNING_SECRET=$SLACK_SIGNING_SECRET/GOOGLE_CLOUD_PROJECT_ID=$GOOGLE_CLOUD_PROJECT_ID/GOOGLE_CLOUD_STORAGE_BUCKET_NAME=$GOOGLE_CLOUD_STORAGE_BUCKET_NAME"

/**
 * Refer to https://cloud.google.com/functions/docs/first-java for details.
 */
public class SlackOAuth extends SlackOAuthFunction {
    private static final App app = new App(AppConfig.builder()
            .clientId(System.getenv("SLACK_CLIENT_ID"))
            .clientSecret(System.getenv("SLACK_CLIENT_SECRET"))
            .scope(System.getenv("SLACK_SCOPES"))
            .signingSecret(System.getenv("SLACK_SIGNING_SECRET"))
            .appPath("/my-first-function")
            .oauthInstallPath("/install")
            .oauthRedirectUriPath("/oauth_redirect")
            .build()
    ).asOAuthApp(true);

    private static final Gson gson = GsonFactory.createSnakeCase(app.config().getSlack().getConfig());

    static class CloudStorageOAuthStateService implements OAuthStateService {
        private static final String BLOB_NAME_PREFIX = "slack-app-oauth-states/";
        private final Storage storage;
        private final String bucketName;

        CloudStorageOAuthStateService(Storage storage, String bucketName) {
            this.storage = storage;
            this.bucketName = bucketName;
        }

        @Override
        public void addNewStateToDatastore(String state) throws Exception {
            BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(bucketName, BLOB_NAME_PREFIX + state))
                    .setContentType("text/plain")
                    .build();
            storage.create(blobInfo, state.getBytes(StandardCharsets.UTF_8));
        }

        @Override
        public boolean isAvailableInDatabase(String state) {
            return storage.get(BlobId.of(bucketName, BLOB_NAME_PREFIX + state)).exists();
        }

        @Override
        public void deleteStateFromDatastore(String state) throws Exception {
            storage.delete(BlobId.of(bucketName, BLOB_NAME_PREFIX + state));
        }
    }

    static class CloudStorageInstallationService implements InstallationService {
        private static final String BLOB_NAME_PREFIX = "slack-app-installations/";
        private final Storage storage;
        private final String bucketName;

        CloudStorageInstallationService(Storage storage, String bucketName) {
            this.storage = storage;
            this.bucketName = bucketName;
        }

        @Override
        public boolean isHistoricalDataEnabled() {
            return false;
        }

        @Override
        public void setHistoricalDataEnabled(boolean b) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void saveInstallerAndBot(Installer installer) throws Exception {
            String workspaceKey = orNone(installer.getEnterpriseId()) + "-" + orNone(installer.getTeamId());
            // store an installer
            BlobInfo installerBlob = BlobInfo
                    .newBuilder(installerBlobId(installer))
                    .setContentType("text/json")
                    .build();
            byte[] installerData = gson.toJson(installer).getBytes(StandardCharsets.UTF_8);
            storage.create(installerBlob, installerData);
            // store a bot
            Bot bot = installer.toBot();
            BlobInfo botBlob = BlobInfo
                    .newBuilder(botBlobId(bot))
                    .setContentType("text/json")
                    .build();
            byte[] botData = gson.toJson(bot).getBytes(StandardCharsets.UTF_8);
            storage.create(botBlob, botData);
        }

        @Override
        public void deleteBot(Bot bot) throws Exception {
            storage.delete(botBlobId(bot));
        }

        @Override
        public void deleteInstaller(Installer installer) throws Exception {
            storage.delete(installerBlobId(installer));
        }

        @Override
        public Bot findBot(String enterpriseId, String teamId) {
            Blob blob = storage.get(botBlobId(enterpriseId, teamId));
            String json = new String(blob.getContent(), StandardCharsets.UTF_8);
            return gson.fromJson(json, DefaultBot.class);
        }

        @Override
        public Installer findInstaller(String enterpriseId, String teamId, String userId) {
            Blob blob = storage.get(installerBlobId(enterpriseId, teamId, userId));
            String json = new String(blob.getContent(), StandardCharsets.UTF_8);
            return gson.fromJson(json, DefaultInstaller.class);
        }

        private static String orNone(String key) {
            return key != null ? key : "none";
        }

        private static String workspaceKey(String e, String t) {
            return orNone(e) + "-" + orNone(t);
        }

        private static String workspaceKey(Installer installer) {
            return workspaceKey(installer.getEnterpriseId(), installer.getTeamId());
        }

        private static String workspaceKey(Bot bot) {
            return workspaceKey(bot.getEnterpriseId(), bot.getTeamId());
        }

        private BlobId botBlobId(Bot bot) {
            return BlobId.of(bucketName, BLOB_NAME_PREFIX + workspaceKey(bot));
        }

        private BlobId botBlobId(String e, String t) {
            return BlobId.of(bucketName, BLOB_NAME_PREFIX + workspaceKey(e, t));
        }

        private BlobId installerBlobId(String e, String t, String u) {
            return BlobId.of(bucketName, BLOB_NAME_PREFIX + workspaceKey(e, t) + "-" + orNone(u));
        }

        private BlobId installerBlobId(Installer installer) {
            return installerBlobId(installer.getEnterpriseId(), installer.getTeamId(), installer.getInstallerUserId());
        }
    }

    static {
        System.setProperty("org.slf4j.simpleLogger.log.com.slack.api", "debug");

        String projectId = System.getenv("GOOGLE_CLOUD_PROJECT_ID");
        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        String bucketName = System.getenv("GOOGLE_CLOUD_STORAGE_BUCKET_NAME");

        app.service(new CloudStorageInstallationService(storage, bucketName));
        app.service(new CloudStorageOAuthStateService(storage, bucketName));

        app.command("/test-google-cloud-app", (req, ctx) -> {
            return ctx.ack("Hi from Google Cloud Functions!");
        });
    }

    public SlackOAuth() {
        super(app);
    }
}