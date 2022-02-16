package test_locally.api.methods.metrics;

import com.slack.api.methods.metrics.MemoryMetricsDatastore;
import com.slack.api.rate_limits.metrics.RequestStats;
import com.slack.api.util.json.GsonFactory;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.slack.api.methods.MethodsConfig.DEFAULT_SINGLETON_EXECUTOR_NAME;
import static org.junit.Assert.*;

public class MemoryMetricsDatastoreTest {

    @Test
    public void getNumberOfNodes() {
        MemoryMetricsDatastore datastore = new MemoryMetricsDatastore(3);
        assertEquals(3, datastore.getNumberOfNodes());
    }

    @Test
    public void getAllStats() {
        MemoryMetricsDatastore datastore = new MemoryMetricsDatastore(1);
        Map<String, Map<String, RequestStats>> allStats = datastore.getAllStats();
        assertNotNull(allStats);
    }

    @Test
    public void getStats_teamId() {
        MemoryMetricsDatastore datastore = new MemoryMetricsDatastore(1);
        RequestStats stats = datastore.getStats("T123");
        assertNotNull(stats);
    }

    @Test
    public void getStats() {
        MemoryMetricsDatastore datastore = new MemoryMetricsDatastore(1);
        RequestStats stats = datastore.getStats(DEFAULT_SINGLETON_EXECUTOR_NAME, "T123");
        assertNotNull(stats);
    }

    @Test
    public void increment() {
        MemoryMetricsDatastore datastore = new MemoryMetricsDatastore(1);
        datastore.incrementAllCompletedCalls(DEFAULT_SINGLETON_EXECUTOR_NAME, "T123", "chat.postMessage");
        datastore.incrementFailedCalls(DEFAULT_SINGLETON_EXECUTOR_NAME, "T123", "chat.postMessage");
        datastore.setRateLimitedMethodRetryEpochMillis(
                DEFAULT_SINGLETON_EXECUTOR_NAME, "T123", "chat.postMessage", 123L);
        datastore.addToWaitingMessageIds(
                DEFAULT_SINGLETON_EXECUTOR_NAME, null, "chat.postMessage", "id");
        datastore.deleteFromWaitingMessageIds(
                DEFAULT_SINGLETON_EXECUTOR_NAME, null, "chat.postMessage", "id");
    }

    @Test
    public void threadGroupName() {
        MemoryMetricsDatastore datastore1 = new MemoryMetricsDatastore(1);
        MemoryMetricsDatastore datastore2 = new MemoryMetricsDatastore(1);
        assertNotEquals(datastore1.getThreadGroupName(), datastore2.getThreadGroupName());
    }

    private static final String[] methodNames = "admin.analytics.getFile,admin.apps.approve,admin.apps.clearResolution,admin.apps.restrict,admin.apps.uninstall,admin.apps.approved.list,admin.apps.requests.cancel,admin.apps.requests.list,admin.apps.restricted.list,admin.auth.policy.assignEntities,admin.auth.policy.getEntities,admin.auth.policy.removeEntities,admin.barriers.create,admin.barriers.delete,admin.barriers.list,admin.barriers.update,admin.conversations.archive,admin.conversations.convertToPrivate,admin.conversations.create,admin.conversations.delete,admin.conversations.disconnectShared,admin.conversations.getConversationPrefs,admin.conversations.getCustomRetention,admin.conversations.getTeams,admin.conversations.invite,admin.conversations.removeCustomRetention,admin.conversations.rename,admin.conversations.search,admin.conversations.setConversationPrefs,admin.conversations.setCustomRetention,admin.conversations.setTeams,admin.conversations.unarchive,admin.conversations.ekm.listOriginalConnectedChannelInfo,admin.conversations.restrictAccess.addGroup,admin.conversations.restrictAccess.listGroups,admin.conversations.restrictAccess.removeGroup,admin.emoji.add,admin.emoji.addAlias,admin.emoji.list,admin.emoji.remove,admin.emoji.rename,admin.inviteRequests.approve,admin.inviteRequests.deny,admin.inviteRequests.list,admin.inviteRequests.approved.list,admin.inviteRequests.denied.list,admin.teams.admins.list,admin.teams.create,admin.teams.list,admin.teams.owners.list,admin.teams.settings.info,admin.teams.settings.setDefaultChannels,admin.teams.settings.setDescription,admin.teams.settings.setDiscoverability,admin.teams.settings.setIcon,admin.teams.settings.setName,admin.usergroups.addChannels,admin.usergroups.addTeams,admin.usergroups.listChannels,admin.usergroups.removeChannels,admin.users.assign,admin.users.invite,admin.users.list,admin.users.remove,admin.users.setAdmin,admin.users.setExpiration,admin.users.setOwner,admin.users.setRegular,admin.users.session.clearSettings,admin.users.session.getSettings,admin.users.session.invalidate,admin.users.session.list,admin.users.session.reset,admin.users.session.resetBulk,admin.users.session.setSettings,admin.users.unsupportedVersions.export,api.test,apps.connections.open,apps.event.authorizations.list,apps.manifest.create,apps.manifest.delete,apps.manifest.export,apps.manifest.update,apps.manifest.validate,apps.uninstall,auth.revoke,auth.test,auth.teams.list,bookmarks.add,bookmarks.edit,bookmarks.list,bookmarks.remove,bots.info,calls.add,calls.end,calls.info,calls.update,calls.participants.add,calls.participants.remove,chat.delete,chat.deleteScheduledMessage,chat.getPermalink,chat.meMessage,chat.postEphemeral,chat.postMessage,chat.scheduleMessage,chat.unfurl,chat.update,chat.scheduledMessages.list,conversations.acceptSharedInvite,conversations.approveSharedInvite,conversations.archive,conversations.close,conversations.create,conversations.declineSharedInvite,conversations.history,conversations.info,conversations.invite,conversations.inviteShared,conversations.join,conversations.kick,conversations.leave,conversations.list,conversations.listConnectInvites,conversations.mark,conversations.members,conversations.open,conversations.rename,conversations.replies,conversations.setPurpose,conversations.setTopic,conversations.unarchive,dialog.open,dnd.endDnd,dnd.endSnooze,dnd.info,dnd.setSnooze,dnd.teamInfo,emoji.list,files.comments.delete,files.delete,files.info,files.list,files.revokePublicURL,files.sharedPublicURL,files.upload,files.remote.add,files.remote.info,files.remote.list,files.remote.remove,files.remote.share,files.remote.update,migration.exchange,oauth.access,oauth.v2.access,oauth.v2.exchange,openid.connect.token,openid.connect.userInfo,pins.add,pins.list,pins.remove,reactions.add,reactions.get,reactions.list,reactions.remove,reminders.add,reminders.complete,reminders.delete,reminders.info,reminders.list,rtm.connect,rtm.start,search.all,search.files,search.messages,stars.add,stars.list,stars.remove,team.accessLogs,team.billableInfo,team.info,team.integrationLogs,team.billing.info,team.preferences.list,team.profile.get,tooling.tokens.rotate,usergroups.create,usergroups.disable,usergroups.enable,usergroups.list,usergroups.update,usergroups.users.list,usergroups.users.update,users.conversations,users.deletePhoto,users.getPresence,users.identity,users.info,users.list,users.lookupByEmail,users.setActive,users.setPhoto,users.setPresence,users.profile.get,users.profile.set,views.open,views.publish,views.push,views.update,workflows.stepCompleted,workflows.stepFailed,workflows.updateStep,channels.create,channels.info,channels.invite,channels.mark,groups.create,groups.info,groups.invite,groups.mark,groups.open,im.list,im.mark,im.open,mpim.list,mpim.mark,mpim.open".split(",");

    // TODO
    @Ignore
    @Test
    public void issue_933_MetricsMaintenanceJobLoad() throws Exception {
        MemoryMetricsDatastore datastore = new MemoryMetricsDatastore(1);
        List<String> methodsForTests = Arrays.stream(methodNames).limit(30).collect(Collectors.toList());
        for (int i = 0; i < 600; i++) {
            String teamId = "T" + i;
            for (String methodName : methodsForTests) {
                datastore.incrementAllCompletedCalls(DEFAULT_SINGLETON_EXECUTOR_NAME, teamId, methodName);
            }
        }
        while (true) {
            System.out.println(GsonFactory.createSnakeCase().toJson(datastore.getAllStats()));
            Thread.sleep(5_000L);
        }
    }
}
