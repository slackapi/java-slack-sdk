package com.slack.api.bolt.service.builtin;

import com.slack.api.bolt.Initializer;
import com.slack.api.bolt.service.OAuthStateService;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol.ResponseKeyword;
import redis.clients.jedis.params.SetParams;

/**
 * OAuthStateService implementation using Redis (or ValKey)
 *
 * @see <a href="https://aws.amazon.com/elasticache/">Amazon ElastiCache</a>
 */
@Slf4j
public class RedisOAuthStateService implements OAuthStateService {

    private final JedisPool jedisPool;

    public RedisOAuthStateService(JedisPool jedisPool) {
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
    public void addNewStateToDatastore(String state) throws Exception {
        try (Jedis jedis = jedis()) {
            String key = getKey(state);
            jedis.set(key, "", SetParams.setParams().ex(getExpirationInSeconds()));
        }
    }

    @Override
    public boolean isAvailableInDatabase(String state) {
        try (Jedis jedis = jedis()) {
            String key = getKey(state);
            return jedis.exists(key);
        }
    }

    @Override
    public void deleteStateFromDatastore(String state) throws Exception {
        try (Jedis jedis = jedis()) {
            String key = getKey(state);
            jedis.del(key);
        }
    }

    Jedis jedis() {
        return jedisPool.getResource();
    }

    String getKey(String state) {
        return "state/" + state;
    }

}
