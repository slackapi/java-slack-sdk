package test_locally.api.scim;

import com.slack.api.SlackConfig;
import com.slack.api.scim.SCIMApiCompletionException;
import com.slack.api.scim.SCIMApiException;
import com.slack.api.scim.SCIMEndpointName;
import com.slack.api.scim.impl.AsyncRateLimitExecutor;
import com.slack.api.scim.response.UsersReadResponse;
import com.slack.api.methods.impl.MethodsClientImpl;
import com.slack.api.util.http.SlackHttpClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class AsyncRateLimitExecutorTest {

    private AsyncRateLimitExecutor executor;

    @Before
    public void setup() {
        SlackConfig config = new SlackConfig();
        SlackHttpClient httpClient = new SlackHttpClient();
        MethodsClientImpl clientForTeamIdCache = new MethodsClientImpl(httpClient);
        this.executor = AsyncRateLimitExecutor.getOrCreate(clientForTeamIdCache, config);
    }

    @Test
    public void get_with_invalid_key() {
        String executorName = "invalid-" + System.currentTimeMillis();
        AsyncRateLimitExecutor executor = AsyncRateLimitExecutor.get(executorName);
        assertThat(executor, is(nullValue()));
    }

    @Test
    public void getOrCreate() {
        assertThat(executor, is(notNullValue()));
    }

    @Test
    public void runWithoutQueue() {
        executor.runWithoutQueue("T111", SCIMEndpointName.readUser, () -> new UsersReadResponse());
    }

    @Test(expected = SCIMApiCompletionException.class)
    public void runWithoutQueue_RuntimeException() {
        executor.runWithoutQueue("T111", SCIMEndpointName.readUser, () -> {
            throw new RuntimeException("foo");
        });
    }

    @Test(expected = SCIMApiCompletionException.class)
    public void runWithoutQueue_IOException() {
        executor.runWithoutQueue("T111", SCIMEndpointName.readUser, () -> {
            throw new IOException("foo");
        });
    }

    @Test(expected = SCIMApiCompletionException.class)
    public void runWithoutQueue_AuditApiException() {
        executor.runWithoutQueue("T111", SCIMEndpointName.readUser, () -> {
            throw new SCIMApiException(null, "foo");
        });
    }

}
