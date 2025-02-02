package test_locally.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.slack.api.bolt.model.builtin.DefaultBot;
import com.slack.api.bolt.model.builtin.DefaultInstaller;
import com.slack.api.bolt.service.builtin.RedisInstallationService;
import com.slack.api.bolt.service.builtin.RedisOAuthStateService;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol.ResponseKeyword;

public class RedisInstallationServiceTest {

    static JedisPool setupJedisDongMock(JedisPool jedisPool) {
        Jedis jedis = mock(Jedis.class);
        when(jedis.ping()).thenReturn("DONG");
        when(jedisPool.getResource()).thenReturn(jedis);
        return jedisPool;
    }

    static JedisPool setupJedisPoolMock(JedisPool jedisPool) {
        Jedis jedis = mock(Jedis.class);
        when(jedis.ping()).thenReturn(ResponseKeyword.PONG.name());
        when(jedis.exists(anyString())).thenReturn(true);
        when(jedis.get(anyString())).thenReturn("{}");
        when(jedis.set(anyString(), anyString())).thenReturn("{}");
        when(jedis.del(anyString())).thenReturn(0L);
        when(jedisPool.getResource()).thenReturn(jedis);
        return jedisPool;
    }

    @Test(expected = IllegalStateException.class)
    public void initializer_no_ping() {
        JedisPool jedisPool = setupJedisDongMock(mock(JedisPool.class));
        RedisOAuthStateService service = new RedisOAuthStateService(jedisPool);
        service.initializer().accept(null);
    }

    @Test
    public void initializer() {
        JedisPool jedisPool = setupJedisPoolMock(mock(JedisPool.class));
        RedisOAuthStateService service = new RedisOAuthStateService(jedisPool);
        service.initializer().accept(null);
    }

    @Test
    public void operations() throws Exception {
        JedisPool jedisPool = setupJedisPoolMock(mock(JedisPool.class));
        RedisInstallationService service = new RedisInstallationService(jedisPool);
        service.initializer().accept(null);

        service.saveInstallerAndBot(new DefaultInstaller());
        service.deleteBot(new DefaultBot());
        service.deleteInstaller(new DefaultInstaller());
        service.findBot("E123", "T123");
        service.findInstaller("E123", "T123", "U123");
    }

    @Test
    public void operations_historical_data_enabled() throws Exception {
        JedisPool jedisPool = setupJedisPoolMock(mock(JedisPool.class));
        RedisInstallationService service = new RedisInstallationService(jedisPool);
        service.setHistoricalDataEnabled(true);
        service.initializer().accept(null);

        service.saveInstallerAndBot(new DefaultInstaller());
        service.deleteBot(new DefaultBot());
        service.deleteInstaller(new DefaultInstaller());
        assertNotNull(service.findBot(null, "T123"));
        assertNotNull(service.findBot("E123", null));
        assertNotNull(service.findBot("E123", "T123"));
        assertNotNull(service.findInstaller(null, "T123", "U123"));
        assertNotNull(service.findInstaller("E123", null, "U123"));
        assertNotNull(service.findInstaller("E123", "T123", "U123"));
    }

}
