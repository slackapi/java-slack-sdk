package com.github.seratch.jslack.lightning.service.builtin;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.github.seratch.jslack.lightning.model.Bot;
import com.github.seratch.jslack.lightning.model.Installer;
import com.github.seratch.jslack.lightning.model.builtin.DefaultBot;
import com.github.seratch.jslack.lightning.model.builtin.DefaultInstaller;
import com.github.seratch.jslack.lightning.service.InstallationService;
import com.github.seratch.jslack.lightning.util.JsonOps;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;

@Slf4j
public class AmazonS3InstallationService implements InstallationService {

    private final String bucketName;

    public AmazonS3InstallationService(String bucketName) {
        this.bucketName = bucketName;
    }

    @Override
    public void saveInstallerAndBot(Installer i) throws Exception {
        AmazonS3 s3 = this.createS3Client();
        save(s3, getInstallerKey(i), JsonOps.toJsonString(i), "AWS S3 putObject result of Installer data - {}");
        save(s3, getBotKey(i), JsonOps.toJsonString(i.toBot()), "AWS S3 putObject result of Bot data - {}");
    }

    private void save(AmazonS3 s3, String s3Key, String json, String logMessage) {
        PutObjectResult botPutResult = s3.putObject(bucketName, s3Key, json);
        if (log.isDebugEnabled()) {
            log.debug(logMessage, JsonOps.toJsonString(botPutResult));
        }
    }

    @Override
    public void deleteBot(Bot bot) throws Exception {
        AmazonS3 s3 = this.createS3Client();
        s3.deleteObject(bucketName, getBotKey(bot.getEnterpriseId(), bot.getTeamId()));
    }

    @Override
    public void deleteInstaller(Installer installer) throws Exception {
        AmazonS3 s3 = this.createS3Client();
        s3.deleteObject(bucketName, getInstallerKey(installer));
    }

    @Override
    public Bot findBot(String enterpriseId, String teamId) {
        AmazonS3 s3 = this.createS3Client();
        String fullKey = getBotKey(enterpriseId, teamId);
        if (s3.getObjectMetadata(bucketName, fullKey) == null && enterpriseId != null) {
            String nonGridKey = getBotKey(null, teamId);
            S3Object nonGridObject = s3.getObject(bucketName, nonGridKey);
            if (nonGridObject != null) {
                try {
                    Bot bot = toBot(nonGridObject);
                    bot.setEnterpriseId(enterpriseId); // the workspace seems to be in a Grid org now
                    save(s3, fullKey, JsonOps.toJsonString(bot), "AWS S3 putObject result of Bot data - {}");
                    return bot;
                } catch (Exception e) {
                    log.error("Failed to save a new Bot data for enterprise_id: {}, team_id: {}", enterpriseId, teamId);
                }
            }
        }
        S3Object s3Object = s3.getObject(bucketName, fullKey);
        try {
            return toBot(s3Object);
        } catch (IOException e) {
            log.error("Failed to load Bot data for enterprise_id: {}, team_id: {}", enterpriseId, teamId);
            return null;
        }
    }

    @Override
    public Installer findInstaller(String enterpriseId, String teamId, String userId) {
        AmazonS3 s3 = this.createS3Client();
        String fullKey = getInstallerKey(enterpriseId, teamId, userId);
        if (s3.getObjectMetadata(bucketName, fullKey) == null && enterpriseId != null) {
            String nonGridKey = getInstallerKey(null, teamId, userId);
            S3Object nonGridObject = s3.getObject(bucketName, nonGridKey);
            if (nonGridObject != null) {
                try {
                    Installer installer = toInstaller(nonGridObject);
                    installer.setEnterpriseId(enterpriseId); // the workspace seems to be in a Grid org now
                    saveInstallerAndBot(installer);
                    return installer;
                } catch (Exception e) {
                    log.error("Failed to save a new Installer data for enterprise_id: {}, team_id: {}, user_id: {}",
                            enterpriseId, teamId, userId);
                }
            }
        }
        S3Object s3Object = s3.getObject(bucketName, fullKey);
        try {
            return toInstaller(s3Object);
        } catch (Exception e) {
            log.error("Failed to save a new Installer data for enterprise_id: {}, team_id: {}, user_id: {}",
                    enterpriseId, teamId, userId);
            return null;
        }
    }

    private Bot toBot(S3Object s3Object) throws IOException {
        String json = IOUtils.toString(s3Object.getObjectContent());
        return JsonOps.fromJson(json, DefaultBot.class);
    }

    private Installer toInstaller(S3Object s3Object) throws IOException {
        String json = IOUtils.toString(s3Object.getObjectContent());
        return JsonOps.fromJson(json, DefaultInstaller.class);
    }

    private AmazonS3 createS3Client() {
        return AmazonS3ClientBuilder.defaultClient();
    }

    private String getInstallerKey(Installer i) {
        return getInstallerKey(i.getEnterpriseId(), i.getTeamId(), i.getInstallerUserId());
    }

    private String getInstallerKey(String enterpriseId, String teamId, String userId) {
        return "installer/"
                + Optional.ofNullable(enterpriseId).orElse("none") + "-"
                + teamId + "-"
                + userId;
    }

    private String getBotKey(Installer i) {
        return getBotKey(i.getEnterpriseId(), i.getTeamId());
    }

    private String getBotKey(String enterpriseId, String teamId) {
        return "bot/"
                + Optional.ofNullable(enterpriseId).orElse("none") + "-"
                + teamId;
    }
}
