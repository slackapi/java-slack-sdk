package test_locally.api;

import com.slack.api.methods.Methods;
import com.slack.api.methods.MethodsRateLimitTier;
import com.slack.api.methods.MethodsRateLimits;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.fail;

public class MethodsTest {

    @Test
    public void verifyTheCoverage() {
        // https://api.slack.com/methods
        // var methodNames = [].slice.call(document.getElementsByClassName('apiReferenceFilterableList__listItemLink')).map(e => e.href.replace("https://api.slack.com/methods/", ""));console.log(methodNames.toString());console.log(methodNames.length);
        // 248 endpoints as of April 6, 2023
        String methods = "admin.analytics.getFile,admin.apps.approve,admin.apps.clearResolution,admin.apps.restrict,admin.apps.uninstall,admin.apps.approved.list,admin.apps.requests.cancel,admin.apps.requests.list,admin.apps.restricted.list,admin.audit.anomaly.allow.getItem,admin.audit.anomaly.allow.updateItem,admin.auth.policy.assignEntities,admin.auth.policy.getEntities,admin.auth.policy.removeEntities,admin.barriers.create,admin.barriers.delete,admin.barriers.list,admin.barriers.update,admin.conversations.archive,admin.conversations.bulkArchive,admin.conversations.bulkDelete,admin.conversations.bulkMove,admin.conversations.convertToPrivate,admin.conversations.convertToPublic,admin.conversations.create,admin.conversations.delete,admin.conversations.disconnectShared,admin.conversations.getConversationPrefs,admin.conversations.getCustomRetention,admin.conversations.getTeams,admin.conversations.invite,admin.conversations.lookup,admin.conversations.removeCustomRetention,admin.conversations.rename,admin.conversations.search,admin.conversations.setConversationPrefs,admin.conversations.setCustomRetention,admin.conversations.setTeams,admin.conversations.unarchive,admin.conversations.ekm.listOriginalConnectedChannelInfo,admin.conversations.restrictAccess.addGroup,admin.conversations.restrictAccess.listGroups,admin.conversations.restrictAccess.removeGroup,admin.emoji.add,admin.emoji.addAlias,admin.emoji.list,admin.emoji.remove,admin.emoji.rename,admin.inviteRequests.approve,admin.inviteRequests.deny,admin.inviteRequests.list,admin.inviteRequests.approved.list,admin.inviteRequests.denied.list,admin.roles.addAssignments,admin.roles.listAssignments,admin.roles.removeAssignments,admin.teams.admins.list,admin.teams.create,admin.teams.list,admin.teams.owners.list,admin.teams.settings.info,admin.teams.settings.setDefaultChannels,admin.teams.settings.setDescription,admin.teams.settings.setDiscoverability,admin.teams.settings.setIcon,admin.teams.settings.setName,admin.usergroups.addChannels,admin.usergroups.addTeams,admin.usergroups.listChannels,admin.usergroups.removeChannels,admin.users.assign,admin.users.invite,admin.users.list,admin.users.remove,admin.users.setAdmin,admin.users.setExpiration,admin.users.setOwner,admin.users.setRegular,admin.users.session.clearSettings,admin.users.session.getSettings,admin.users.session.invalidate,admin.users.session.list,admin.users.session.reset,admin.users.session.resetBulk,admin.users.session.setSettings,admin.users.unsupportedVersions.export,api.test,apps.auth.external.delete,apps.auth.external.get,apps.connections.open,apps.uninstall,apps.datastore.delete,apps.datastore.get,apps.datastore.put,apps.datastore.query,apps.datastore.update,apps.event.authorizations.list,apps.manifest.create,apps.manifest.delete,apps.manifest.export,apps.manifest.update,apps.manifest.validate,auth.revoke,auth.test,auth.teams.list,bookmarks.add,bookmarks.edit,bookmarks.list,bookmarks.remove,bots.info,calls.add,calls.end,calls.info,calls.update,calls.participants.add,calls.participants.remove,chat.delete,chat.deleteScheduledMessage,chat.getPermalink,chat.meMessage,chat.postEphemeral,chat.postMessage,chat.scheduleMessage,chat.unfurl,chat.update,chat.scheduledMessages.list,conversations.acceptSharedInvite,conversations.approveSharedInvite,conversations.archive,conversations.close,conversations.create,conversations.declineSharedInvite,conversations.history,conversations.info,conversations.invite,conversations.inviteShared,conversations.join,conversations.kick,conversations.leave,conversations.list,conversations.listConnectInvites,conversations.mark,conversations.members,conversations.open,conversations.rename,conversations.replies,conversations.setPurpose,conversations.setTopic,conversations.unarchive,dialog.open,dnd.endDnd,dnd.endSnooze,dnd.info,dnd.setSnooze,dnd.teamInfo,emoji.list,files.completeUploadExternal,files.delete,files.getUploadURLExternal,files.info,files.list,files.revokePublicURL,files.sharedPublicURL,files.upload,files.comments.delete,files.remote.add,files.remote.info,files.remote.list,files.remote.remove,files.remote.share,files.remote.update,functions.workflows.steps.list,functions.workflows.steps.responses.export,migration.exchange,oauth.access,oauth.v2.access,oauth.v2.exchange,openid.connect.token,openid.connect.userInfo,pins.add,pins.list,pins.remove,reactions.add,reactions.get,reactions.list,reactions.remove,reminders.add,reminders.complete,reminders.delete,reminders.info,reminders.list,rtm.connect,rtm.start,search.all,search.files,search.messages,stars.add,stars.list,stars.remove,team.accessLogs,team.billableInfo,team.info,team.integrationLogs,team.billing.info,team.preferences.list,team.profile.get,tooling.tokens.rotate,usergroups.create,usergroups.disable,usergroups.enable,usergroups.list,usergroups.update,usergroups.users.list,usergroups.users.update,users.conversations,users.deletePhoto,users.getPresence,users.identity,users.info,users.list,users.lookupByEmail,users.setActive,users.setPhoto,users.setPresence,users.profile.get,users.profile.set,views.open,views.publish,views.push,views.update,workflows.stepCompleted,workflows.stepFailed,workflows.updateStep,channels.create,channels.info,channels.invite,channels.mark,groups.create,groups.info,groups.invite,groups.mark,groups.open,im.list,im.mark,im.open,mpim.list,mpim.mark,mpim.open";
        final List<String> existingMethods = new ArrayList<>();
        for (Field f : Methods.class.getDeclaredFields()) {
            int modifiers = f.getModifiers();
            if (Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers)) {
                try {
                    String value = f.get(null).toString();
                    existingMethods.add(value);
                } catch (IllegalAccessException e) {
                    fail(e.getMessage());
                }
            }
        }

        String[] allMethodNames = methods.split(",");
        List<String> excludedMethodNames = Arrays.asList(
                // TODO: Add apps.manifest.* APIs
                "apps.manifest.create",
                "apps.manifest.delete",
                "apps.manifest.export",
                "apps.manifest.update",
                "apps.manifest.validate",
                "tooling.tokens.rotate",
                // TODO: next-generation platform related stuff
                "apps.auth.external.delete",
                "apps.auth.external.get",
                "apps.datastore.delete",
                "apps.datastore.get",
                "apps.datastore.put",
                "apps.datastore.query",
                "apps.datastore.update",
                "functions.workflows.steps.list",
                "functions.workflows.steps.responses.export",
                // TODO: admin.audit.anomaly.allow.*
                // The endpoints requires a "session" token
                "admin.audit.anomaly.allow.getItem",
                "admin.audit.anomaly.allow.updateItem"
        );
        List<String> methodNames = new ArrayList<>();
        for (String methodName : allMethodNames) {
            if (!excludedMethodNames.contains(methodName)) {
                methodNames.add(methodName);
            }
        }

        for (String method : methodNames) {
            if (!existingMethods.contains(method)) {
                fail(method + " is not supported yet!");
            }
        }

        for (String method : methodNames) {
            MethodsRateLimitTier tier = MethodsRateLimits.lookupRateLimitTier(method);
            if (tier == null) {
                fail(method + "'s Rate Limit Tier is not added yet!");
            }
        }
    }
}
