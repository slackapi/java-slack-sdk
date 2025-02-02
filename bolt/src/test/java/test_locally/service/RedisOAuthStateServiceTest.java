package test_locally.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.slack.api.bolt.service.builtin.RedisOAuthStateService;
import java.util.UUID;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol.ResponseKeyword;

public class RedisOAuthStateServiceTest {

    static JedisPool setupJedisDongMock(JedisPool jedisPool) {
        Jedis jedis = mock(Jedis.class);
        when(jedis.ping()).thenReturn("DONG");
        when(jedisPool.getResource()).thenReturn(jedis);
        return jedisPool;
    }

    static JedisPool setupJedisPoolMock(JedisPool jedisPool) {
        Jedis jedis = mock(Jedis.class);
        when(jedis.ping()).thenReturn(ResponseKeyword.PONG.name());
        when(jedis.exists(anyString())).thenReturn(false);
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
        RedisOAuthStateService service = new RedisOAuthStateService(jedisPool);
        service.initializer().accept(null);

        String uuid = UUID.randomUUID().toString();
        service.isAvailableInDatabase(uuid);
        service.addNewStateToDatastore(uuid);
        service.deleteStateFromDatastore(uuid);
    }

}
