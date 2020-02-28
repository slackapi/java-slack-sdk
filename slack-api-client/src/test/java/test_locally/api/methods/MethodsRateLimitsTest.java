package test_locally.api.methods;

import com.slack.api.methods.MethodsRateLimitTier;
import com.slack.api.methods.MethodsRateLimits;
import org.junit.Test;

import static org.junit.Assert.assertNull;

public class MethodsRateLimitsTest {

    @Test
    public void lookup_not_found() {
        assertNull(MethodsRateLimits.lookupRateLimitTier("foo.bar"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void setRateLimitTier_found() {
        MethodsRateLimits.setRateLimitTier("api.test", MethodsRateLimitTier.Tier1);
    }
}
