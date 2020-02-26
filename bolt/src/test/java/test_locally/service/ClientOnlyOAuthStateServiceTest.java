package test_locally.service;

import com.slack.api.bolt.service.builtin.ClientOnlyOAuthStateService;
import org.junit.Test;

public class ClientOnlyOAuthStateServiceTest {

    @Test
    public void initializer() {
        ClientOnlyOAuthStateService service = new ClientOnlyOAuthStateService();
        service.initializer().accept(null);
    }

    @Test
    public void operations() throws Exception {
        ClientOnlyOAuthStateService service = new ClientOnlyOAuthStateService();
        service.initializer().accept(null);

        service.isAvailableInDatabase("foo");
        service.addNewStateToDatastore("foo");
        service.deleteStateFromDatastore("foo");
    }

}
