package test_locally.service;

import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.service.builtin.FileOAuthStateService;
import java.util.UUID;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileOAuthStateServiceTest {

    @Test
    public void initializer() {
        FileOAuthStateService service = new FileOAuthStateService(AppConfig.builder().build(), "target/files");
        service.initializer().accept(null);
    }

    @Test
    public void operations() throws Exception {
        FileOAuthStateService service = new FileOAuthStateService(AppConfig.builder().build(), "target/files");
        service.initializer().accept(null);

        String uuid = UUID.randomUUID().toString();
        assertFalse(service.isAvailableInDatabase(uuid));
        service.addNewStateToDatastore(uuid);
        assertTrue(service.isAvailableInDatabase(uuid));
        service.deleteStateFromDatastore(uuid);
        assertFalse(service.isAvailableInDatabase(uuid));
    }

}
