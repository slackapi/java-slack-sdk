package test_locally.api.scim;

import com.slack.api.SlackConfig;
import com.slack.api.scim.model.User;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ModelsTest {

    @Test
    public void extensions() {
        User user = new User();
        user.setId("W1234567890");
        User.Extension enterprise = new User.Extension();
        enterprise.setDivision("Engineering");
        user.setExtension(User.Extension.builder()
                .employeeNumber("123-456")
                .organization("PDE")
                .division("Product")
                .department("Product")
                .build());
        user.setSlackGuest(User.SlackGuest.builder()
                .type("multi")
                .expiration("2020-11-30T23:59:59Z")
                .build());

        assertNotNull(user);
        assertNotNull(user.getExtension().getDivision());
        assertNotNull(user.getSlackGuest().getType());

        String json = GsonFactory.createCamelCase(SlackConfig.DEFAULT).toJson(user);
        String expectedJson = "{\"schemas\":[\"urn:scim:schemas:core:1.0\",\"urn:scim:schemas:extension:enterprise:1.0\",\"urn:scim:schemas:extension:slack:guest:1.0\"],\"id\":\"W1234567890\",\"urn:scim:schemas:extension:enterprise:1.0\":{\"employeeNumber\":\"123-456\",\"organization\":\"PDE\",\"division\":\"Product\",\"department\":\"Product\"},\"urn:scim:schemas:extension:slack:guest:1.0\":{\"type\":\"multi\",\"expiration\":\"2020-11-30T23:59:59Z\"}}";
        assertEquals(expectedJson, json);
    }
}
