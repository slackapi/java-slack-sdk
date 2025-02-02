package com.slack.api.bolt.service.builtin;

import com.slack.api.bolt.Initializer;
import com.slack.api.bolt.model.Bot;
import com.slack.api.bolt.model.Installer;
import com.slack.api.bolt.model.builtin.DefaultBot;
import com.slack.api.bolt.model.builtin.DefaultInstaller;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.bolt.util.JsonOps;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol.ResponseKeyword;

/**
 * InstallationService implementation using Redis (or ValKey)
 *
 * @see <a href="https://aws.amazon.com/elasticache/">Amazon ElastiCache</a>
 */
@Slf4j
public class RedisInstallationService implements InstallationService {

    private final JedisPool jedisPool;
    private boolean historicalDataEnabled;

    public RedisInstallationService(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public Initializer initializer() {
        return (app) -> {
            try (Jedis jedis = jedisPool.getResource()) {
                String pong = jedis.ping();
                if (!pong.equals(ResponseKeyword.PONG.name())) {
                    throw new IllegalStateException("Failed to ping redis");
                }

                log.debug("successfully initialized connection to redis");
            } catch (Exception e) {
                throw new IllegalStateException("Failed to access redis", e);
            }
        };
    }

    @Override
    public boolean isHistoricalDataEnabled() {
        return historicalDataEnabled;
    }

    @Override
    public void setHistoricalDataEnabled(boolean isHistoricalDataEnabled) {
        this.historicalDataEnabled = isHistoricalDataEnabled;
    }

    @Override
    public void saveInstallerAndBot(Installer installer) throws Exception {
        save(getInstallerPath(installer),
                installer.getInstalledAt(),
                JsonOps.toJsonString(installer));
        save(getBotPath(installer.getEnterpriseId(), installer.getTeamId()),
                installer.getInstalledAt(),
                JsonOps.toJsonString(installer.toBot()));
    }

    @Override
    public void saveBot(Bot bot) {
        save(getBotPath(bot.getEnterpriseId(), bot.getTeamId()), bot.getInstalledAt(), JsonOps.toJsonString(bot));
    }

    @Override
    public void deleteBot(Bot bot) throws Exception {
        try (Jedis jedis = jedis()) {
            jedis.del(getBotPath(bot.getEnterpriseId(), bot.getTeamId()));
        }
    }

    @Override
    public void deleteInstaller(Installer installer) throws Exception {
        try (Jedis jedis = jedis()) {
            jedis.del(getInstallerPath(installer));
        }
    }

    @Override
    public Bot findBot(String enterpriseId, String teamId) {
        String json;
        if (enterpriseId != null) {
            // try finding org-level bot token first
            json = loadRedisContent(getBotPath(enterpriseId, null));
            if (json != null) {
                return JsonOps.fromJson(json, DefaultBot.class);
            }
            // not found - going to find workspace level installation
        }
        json = loadRedisContent(getBotPath(enterpriseId, teamId));
        if (json == null && enterpriseId != null) {
            json = loadRedisContent(getBotPath(null, teamId));
            if (json != null) {
                Bot bot = JsonOps.fromJson(json, DefaultBot.class);
                bot.setEnterpriseId(enterpriseId);
                save(getBotPath(enterpriseId, teamId), bot.getInstalledAt(), JsonOps.toJsonString(bot));
                return bot;
            }
        }
        if (json == null) {
            return null;
        }
        return JsonOps.fromJson(json, DefaultBot.class);
    }

    @Override
    public Installer findInstaller(String enterpriseId, String teamId, String userId) {
        String json;
        if (enterpriseId != null) {
            // try finding org-level user token first
            json = loadRedisContent(getInstallerPath(enterpriseId, null, userId));
            if (json != null) {
                return JsonOps.fromJson(json, DefaultInstaller.class);
            }
            // not found - going to find workspace level installation
        }
        json = loadRedisContent(getInstallerPath(enterpriseId, teamId, userId));
        if (json == null && enterpriseId != null) {
            json = loadRedisContent(getInstallerPath(null, teamId, userId));
            if (json != null) {
                Installer installer = JsonOps.fromJson(json, DefaultInstaller.class);
                installer.setEnterpriseId(enterpriseId);
                save(getInstallerPath(enterpriseId, teamId, userId),
                    installer.getInstalledAt(),
                    JsonOps.toJsonString(installer));
                return installer;
            }
        }
        if (json == null) {
            return null;
        }
        return JsonOps.fromJson(json, DefaultInstaller.class);
    }

    @Override
    public void deleteAll(String enterpriseId, String teamId) {
        String keyPrefix = Optional.ofNullable(enterpriseId).orElse("none")
            + "-"
            + Optional.ofNullable(teamId).orElse("none");
        try (Jedis jedis = jedis()) {
            jedis.keys(keyPrefix).forEach(jedis::del);
        }
    }

    private String getInstallerPath(Installer installer) {
        return getInstallerPath(installer.getEnterpriseId(),
            installer.getTeamId(),
            installer.getInstallerUserId());
    }

    private String getInstallerPath(String enterpriseId, String teamId, String userId) {
        String key = Optional.ofNullable(enterpriseId).orElse("none")
                + "-"
                + Optional.ofNullable(teamId).orElse("none")
                + "-"
                + userId;
        if (isHistoricalDataEnabled()) {
            key = key + "-latest";
        }
        return "installer/" + key;
    }

    private String getBotPath(String enterpriseId, String teamId) {
        String key = Optional.ofNullable(enterpriseId).orElse("none")
                + "-"
                + Optional.ofNullable(teamId).orElse("none");
        if (isHistoricalDataEnabled()) {
            key = key + "-latest";
        }
        return "bot/" + key;
    }

    private Jedis jedis() {
        return jedisPool.getResource();
    }

    private void save(String path, Long installedAt, String json) {
        try (Jedis jedis = jedis()) {
            // latest
            jedis.set(path, json);

            if (isHistoricalDataEnabled()) {
                // the historical data
                jedis.set(path.replaceFirst("-latest$", "-" + installedAt), json);
            }
        }
    }

    private String loadRedisContent(String path) {
        try (Jedis jedis = jedis()) {
            if (!jedis.exists(path)) {
                return null;
            }
            String content = jedis.get(path);
            if (content.trim().isEmpty() || content.trim().equals("null")) {
                return null;
            }
            return content;
        }
    }

}
