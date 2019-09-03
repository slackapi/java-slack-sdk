package test_locally.api.model;

import com.github.seratch.jslack.api.methods.response.users.UsersInfoResponse;
import com.github.seratch.jslack.api.methods.response.users.UsersListResponse;
import org.junit.Test;

public class UserTest implements Verifier {

    @Test
    public void parseUsersListResponse() throws Exception {
        verifyParsing("users.list", UsersListResponse.class);
    }

    @Test
    public void parseUsersInfoResponse() throws Exception {
        verifyParsing("users.info", UsersInfoResponse.class);
    }

}
