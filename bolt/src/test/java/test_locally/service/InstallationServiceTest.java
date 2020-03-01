package test_locally.service;

import com.slack.api.bolt.model.Bot;
import com.slack.api.bolt.model.Installer;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.model.block.LayoutBlock;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class InstallationServiceTest {

    InstallationService service = new InstallationService() {
        @Override
        public boolean isHistoricalDataEnabled() {
            return false;
        }

        @Override
        public void setHistoricalDataEnabled(boolean isHistoricalDataEnabled) {

        }

        @Override
        public void saveInstallerAndBot(Installer installer) throws Exception {

        }

        @Override
        public void deleteBot(Bot bot) throws Exception {

        }

        @Override
        public void deleteInstaller(Installer installer) throws Exception {

        }

        @Override
        public Bot findBot(String enterpriseId, String teamId) {
            return null;
        }

        @Override
        public Installer findInstaller(String enterpriseId, String teamId, String userId) {
            return null;
        }
    };

    @Test
    public void getInstallationGuideText() {
        String text = service.getInstallationGuideText("E123", "T123", "U123");
        assertNotNull(text);
    }

    @Test
    public void getInstallationGuideBlocks() {
        List<LayoutBlock> blocks = service.getInstallationGuideBlocks("E123", "T123", "U123");
        assertNull(blocks);
    }
}
