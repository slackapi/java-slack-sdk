package test_locally.service;

import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.model.builtin.DefaultBot;
import com.slack.api.bolt.model.builtin.DefaultInstaller;
import com.slack.api.bolt.service.builtin.FileInstallationService;
import org.junit.Test;

public class FileInstallationServiceTest {

    @Test
    public void initializer() {
        FileInstallationService service = new FileInstallationService(AppConfig.builder().build(), "target/files");
        service.initializer().accept(null);
    }

    @Test
    public void operations() throws Exception {
        FileInstallationService service = new FileInstallationService(AppConfig.builder().build(), "target/files");
        service.initializer().accept(null);

        service.saveInstallerAndBot(new DefaultInstaller());
        service.deleteBot(new DefaultBot());
        service.deleteInstaller(new DefaultInstaller());
        service.findBot("E123", "T123");
        service.findInstaller("E123", "T123", "U123");
    }

    @Test
    public void operations_historical_data() throws Exception {
        FileInstallationService service = new FileInstallationService(AppConfig.builder().build(), "target/files");
        service.setHistoricalDataEnabled(true);
        service.initializer().accept(null);

        service.saveInstallerAndBot(new DefaultInstaller());
        service.deleteBot(new DefaultBot());
        service.deleteInstaller(new DefaultInstaller());
        service.findBot("E123", "T123");
        service.findInstaller("E123", "T123", "U123");
    }

}
