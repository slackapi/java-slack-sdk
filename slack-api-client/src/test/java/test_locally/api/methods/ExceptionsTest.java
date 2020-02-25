package test_locally.api.methods;

import com.slack.api.methods.MethodsCompletionException;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ExceptionsTest {

    @Test
    public void createMethodsCompletionException() {
        MethodsCompletionException exception = new MethodsCompletionException(null, null, null);
        assertNotNull(exception);
    }
}
