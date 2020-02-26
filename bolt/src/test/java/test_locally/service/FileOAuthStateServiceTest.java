package test_locally.service;

import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.service.builtin.FileOAuthStateService;
import org.junit.Test;

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

        service.isAvailableInDatabase("foo");
        service.addNewStateToDatastore("foo");
        service.deleteStateFromDatastore("foo");
    }

}
