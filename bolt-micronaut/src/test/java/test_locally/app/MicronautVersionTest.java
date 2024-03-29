package test_locally.app;

import io.micronaut.core.version.VersionUtils;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
public class MicronautVersionTest {

    @Test
    public void expectedMicronautVersion() {
       String version = VersionUtils.getMicronautVersion();
       Assertions.assertTrue(version.matches("4\\.\\d+\\.\\d+"));
    }

}

