package test_locally.api.methods;

import com.slack.api.methods.MethodsCompletionException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ExceptionsTest {

    @Test
    public void createMethodsCompletionException() {
        MethodsCompletionException exception = new MethodsCompletionException(new IOException("IO error"));
        assertNotNull(exception);
        assertEquals("IO error", exception.getMessage());
    }
}
