package test_locally.service;

import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.model.Bot;
import com.slack.api.bolt.model.Installer;
import com.slack.api.bolt.model.builtin.DefaultBot;
import com.slack.api.bolt.model.builtin.DefaultInstaller;
import com.slack.api.bolt.service.builtin.FileInstallationService;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class FileInstallationServiceTest {

    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }

    @Before
    public void cleanup() {
        deleteFolder(new File("target/files"));
    }

    @Test
    public void initializer() {
        FileInstallationService service = new FileInstallationService(AppConfig.builder().build(), "target/files");
        service.initializer().accept(null);
    }

    @Test
    public void operations() throws Exception {
        FileInstallationService service = new FileInstallationService(AppConfig.builder().build(), "target/files");
        service.initializer().accept(null);

        Bot bot = new DefaultBot();
        bot.setEnterpriseId("E123");
        bot.setTeamId("T123");
        Installer installer = new DefaultInstaller() {
            @Override
            public Bot toBot() {
                return bot;
            }
        };
        installer.setEnterpriseId("E123");
        installer.setTeamId("T123");
        installer.setInstallerUserId("U123");

        service.saveInstallerAndBot(installer);

        service.findBot("E123", "T123");
        service.findInstaller("E123", "T123", "U123");

        service.deleteBot(bot);
        service.deleteInstaller(installer);
    }

    static class MyInstaller extends DefaultInstaller {
        Bot bot;
        MyInstaller(Bot bot) {
            this.bot = bot;
        }
        @Override
        public Bot toBot() {
            return bot;
        }
    }

    @Test
    public void grid_migration() throws Exception {
        FileInstallationService service = new FileInstallationService(AppConfig.builder().build(), "target/files");
        service.initializer().accept(null);

        Bot bot = new DefaultBot();
        bot.setTeamId("T123");
        Installer installer = new MyInstaller(bot);
        installer.setTeamId("T123");
        installer.setInstallerUserId("U123");

        service.saveInstallerAndBot(installer);

        service.findBot("E123", "T123");
        service.findInstaller("E123", "T123", "U123");

        service.deleteBot(bot);
        service.deleteInstaller(installer);
    }

    @Test
    public void operations_not_found() throws Exception {
        FileInstallationService service = new FileInstallationService(AppConfig.builder().build(), "target/files");
        service.initializer().accept(null);
        service.findBot("E234", "T234");
        service.findInstaller("E234", "T234", "U234");
    }

    @Test
    public void operations_historical_data() throws Exception {
        FileInstallationService service = new FileInstallationService(AppConfig.builder().build(), "target/files");
        service.setHistoricalDataEnabled(true);
        service.initializer().accept(null);

        Bot bot = new DefaultBot();
        bot.setEnterpriseId("E123");
        bot.setTeamId("T123");
        Installer installer = new DefaultInstaller() {
            @Override
            public Bot toBot() {
                return bot;
            }
        };
        installer.setEnterpriseId("E123");
        installer.setTeamId("T123");
        installer.setBotId("B123");
        installer.setInstallerUserId("U123");

        service.saveInstallerAndBot(installer);
        service.findBot("E123", "T123");
        service.findInstaller("E123", "T123", "U123");

        service.deleteBot(bot);
        service.deleteInstaller(installer);
    }

}
