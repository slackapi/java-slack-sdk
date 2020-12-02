package test_with_remote_apis.methods_admin_api;

import com.slack.api.Slack;
import com.slack.api.methods.AsyncMethodsClient;
import com.slack.api.methods.response.admin.barriers.AdminBarriersCreateResponse;
import com.slack.api.methods.response.admin.barriers.AdminBarriersListResponse;
import com.slack.api.methods.response.admin.barriers.AdminBarriersUpdateResponse;
import com.slack.api.model.admin.InformationBarrier;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class AdminApi_barriers_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    static String orgAdminUserToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN);
    static AsyncMethodsClient methodsAsync = slack.methodsAsync(orgAdminUserToken);
    static String idpUsergroupId = System.getenv(Constants.SLACK_SDK_TEST_GRID_IDP_USERGROUP_ID);
    static String idpUsergroupId2 = System.getenv(Constants.SLACK_SDK_TEST_GRID_IDP_USERGROUP_ID_2);

    @Test
    public void crud() throws Exception {
        if (orgAdminUserToken != null) {
            AdminBarriersListResponse list = methodsAsync.adminBarriersList(r -> r.limit(1000)).get();
            assertThat(list.getError(), is(nullValue()));
            for (InformationBarrier barrier : list.getBarriers()) {
                methodsAsync.adminBarriersDelete(r -> r.barrierId(barrier.getId())).get();
            }

            AdminBarriersCreateResponse creation = methodsAsync.adminBarriersCreate(r -> r
                    .primaryUsergroupId(idpUsergroupId)
                    .barrieredFromUsergroupIds(Arrays.asList(idpUsergroupId2))
                    .restrictedSubjects(Arrays.asList("call", "im", "mpim"))
            ).get();
            assertThat(creation.getError(), is(nullValue()));

            Thread.sleep(1_500L);

            AdminBarriersUpdateResponse modification = methodsAsync.adminBarriersUpdate(r -> r
                    .barrierId(creation.getBarrier().getId())
                    .primaryUsergroupId(idpUsergroupId2)
                    .barrieredFromUsergroupIds(Arrays.asList(idpUsergroupId))
                    .restrictedSubjects(Arrays.asList("call", "im", "mpim"))
            ).get();
            assertThat(modification.getError(), is(nullValue()));
            assertThat(modification.getBarrier().getDateUpdate(),
                    is(not(creation.getBarrier().getDateUpdate())));
        }
    }
}
