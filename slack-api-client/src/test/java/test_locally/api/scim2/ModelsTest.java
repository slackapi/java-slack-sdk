package test_locally.api.scim2;

import com.slack.api.SlackConfig;
import com.slack.api.scim2.model.User;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ModelsTest {

    @Test
    public void extensions() {
        assertEquals(User.SlackGuest.Types.MULTI, "multi");

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
        user.setSlackProfile(User.SlackProfile.builder()
                .startDate("2020-10-31T23:59:59Z")
                .build());

        assertNotNull(user);
        assertNotNull(user.getExtension().getDivision());
        assertNotNull(user.getSlackGuest().getType());
        assertNotNull(user.getSlackProfile().getStartDate());

        String json = GsonFactory.createCamelCase(SlackConfig.DEFAULT).toJson(user);
        String expectedJson = "{\"schemas\":[\"urn:ietf:params:scim:schemas:core:2.0:User\",\"urn:ietf:params:scim:schemas:extension:enterprise:2.0:User\",\"urn:ietf:params:scim:schemas:extension:slack:guest:2.0:User\",\"urn:ietf:params:scim:schemas:extension:slack:profile:2.0:User\"],\"id\":\"W1234567890\",\"urn:ietf:params:scim:schemas:extension:enterprise:2.0:User\":{\"employeeNumber\":\"123-456\",\"organization\":\"PDE\",\"division\":\"Product\",\"department\":\"Product\"},\"urn:ietf:params:scim:schemas:extension:slack:guest:2.0:User\":{\"type\":\"multi\",\"expiration\":\"2020-11-30T23:59:59Z\"},\"urn:ietf:params:scim:schemas:extension:slack:profile:2.0:User\":{\"startDate\":\"2020-10-31T23:59:59Z\"}}";
        assertEquals(expectedJson, json);
    }
}
