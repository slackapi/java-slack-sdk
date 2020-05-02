package test_locally.api.scim;

import com.slack.api.scim.SCIMApiErrorResponse;
import com.slack.api.scim.SCIMApiException;
import com.slack.api.scim.response.*;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EqualityTest {

    @Test
    public void test() {
        Request request = new Request.Builder()
                .url("https://www.example.com/")
                .build();
        Response response = new Response.Builder()
                .request(request)
                .code(200)
                .protocol(Protocol.HTTP_1_1)
                .message("")
                .build();

        assertEquals(new SCIMApiErrorResponse(), new SCIMApiErrorResponse());
        assertEquals(new SCIMApiException(response, ""), new SCIMApiException(response, ""));

        assertEquals(new GroupsCreateResponse(), new GroupsCreateResponse());
        assertEquals(new GroupsDeleteResponse(), new GroupsDeleteResponse());
        assertEquals(new GroupsPatchResponse(), new GroupsPatchResponse());
        assertEquals(new GroupsReadResponse(), new GroupsReadResponse());
        assertEquals(new GroupsSearchResponse(), new GroupsSearchResponse());
        assertEquals(new GroupsUpdateResponse(), new GroupsUpdateResponse());

        assertEquals(new ServiceProviderConfigsGetResponse(), new ServiceProviderConfigsGetResponse());

        assertEquals(new UsersCreateResponse(), new UsersCreateResponse());
        assertEquals(new UsersDeleteResponse(), new UsersDeleteResponse());
        assertEquals(new UsersPatchResponse(), new UsersPatchResponse());
        assertEquals(new UsersReadResponse(), new UsersReadResponse());
        assertEquals(new UsersSearchResponse(), new UsersSearchResponse());
        assertEquals(new UsersUpdateResponse(), new UsersUpdateResponse());
    }
}
