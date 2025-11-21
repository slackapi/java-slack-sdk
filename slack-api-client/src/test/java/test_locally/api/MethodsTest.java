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
        // https://docs.slack.dev/reference/methods
        // var methodNames = [].slice.call(document.getElementsByClassName('apiReferenceFilterableList__listItemLink')).map(e => e.href.replace("https://docs.slack.dev/reference/methods/", ""));console.log(methodNames.toString());console.log(methodNames.length);
        // 303 endpoints as of November 21, 2025
        String methods = "admin.analytics.getFile,admin.apps.activities.list,admin.apps.approve,admin.apps.clearResolution,admin.apps.restrict,admin.apps.uninstall,admin.apps.approved.list,admin.apps.config.lookup,admin.apps.config.set,admin.apps.requests.cancel,admin.apps.requests.list,admin.apps.restricted.list,admin.audit.anomaly.allow.getItem,admin.audit.anomaly.allow.updateItem,admin.auth.policy.assignEntities,admin.auth.policy.getEntities,admin.auth.policy.removeEntities,admin.barriers.create,admin.barriers.delete,admin.barriers.list,admin.barriers.update,admin.conversations.archive,admin.conversations.bulkArchive,admin.conversations.bulkDelete,admin.conversations.bulkMove,admin.conversations.convertToPrivate,admin.conversations.convertToPublic,admin.conversations.create,admin.conversations.createForObjects,admin.conversations.delete,admin.conversations.disconnectShared,admin.conversations.getConversationPrefs,admin.conversations.getCustomRetention,admin.conversations.getTeams,admin.conversations.invite,admin.conversations.linkObjects,admin.conversations.lookup,admin.conversations.removeCustomRetention,admin.conversations.rename,admin.conversations.search,admin.conversations.setConversationPrefs,admin.conversations.setCustomRetention,admin.conversations.setTeams,admin.conversations.unarchive,admin.conversations.unlinkObjects,admin.conversations.ekm.listOriginalConnectedChannelInfo,admin.conversations.restrictAccess.addGroup,admin.conversations.restrictAccess.listGroups,admin.conversations.restrictAccess.removeGroup,admin.emoji.add,admin.emoji.addAlias,admin.emoji.list,admin.emoji.remove,admin.emoji.rename,admin.functions.list,admin.functions.permissions.lookup,admin.functions.permissions.set,admin.inviteRequests.approve,admin.inviteRequests.deny,admin.inviteRequests.list,admin.inviteRequests.approved.list,admin.inviteRequests.denied.list,admin.roles.addAssignments,admin.roles.listAssignments,admin.roles.removeAssignments,admin.teams.admins.list,admin.teams.create,admin.teams.list,admin.teams.owners.list,admin.teams.settings.info,admin.teams.settings.setDefaultChannels,admin.teams.settings.setDescription,admin.teams.settings.setDiscoverability,admin.teams.settings.setIcon,admin.teams.settings.setName,admin.usergroups.addChannels,admin.usergroups.addTeams,admin.usergroups.listChannels,admin.usergroups.removeChannels,admin.users.assign,admin.users.invite,admin.users.list,admin.users.remove,admin.users.setAdmin,admin.users.setExpiration,admin.users.setOwner,admin.users.setRegular,admin.users.session.clearSettings,admin.users.session.getSettings,admin.users.session.invalidate,admin.users.session.list,admin.users.session.reset,admin.users.session.resetBulk,admin.users.session.setSettings,admin.users.unsupportedVersions.export,admin.workflows.collaborators.add,admin.workflows.collaborators.remove,admin.workflows.permissions.lookup,admin.workflows.search,admin.workflows.unpublish,admin.workflows.triggers.types.permissions.lookup,admin.workflows.triggers.types.permissions.set,api.test,apps.activities.list,apps.auth.external.delete,apps.auth.external.get,apps.connections.open,apps.datastore.bulkDelete,apps.datastore.bulkGet,apps.datastore.bulkPut,apps.datastore.count,apps.datastore.delete,apps.datastore.get,apps.datastore.put,apps.datastore.query,apps.datastore.update,apps.event.authorizations.list,apps.manifest.create,apps.manifest.delete,apps.manifest.export,apps.manifest.update,apps.manifest.validate,apps.uninstall,assistant.search.context,assistant.threads.setStatus,assistant.threads.setSuggestedPrompts,assistant.threads.setTitle,auth.revoke,auth.test,auth.teams.list,bookmarks.add,bookmarks.edit,bookmarks.list,bookmarks.remove,bots.info,calls.add,calls.end,calls.info,calls.update,calls.participants.add,calls.participants.remove,canvases.access.delete,canvases.access.set,canvases.create,canvases.delete,canvases.edit,canvases.sections.lookup,channels.mark,chat.appendStream,chat.delete,chat.deleteScheduledMessage,chat.getPermalink,chat.meMessage,chat.postEphemeral,chat.postMessage,chat.scheduleMessage,chat.startStream,chat.stopStream,chat.unfurl,chat.update,chat.scheduledMessages.list,conversations.acceptSharedInvite,conversations.approveSharedInvite,conversations.archive,conversations.close,conversations.create,conversations.declineSharedInvite,conversations.history,conversations.info,conversations.invite,conversations.inviteShared,conversations.join,conversations.kick,conversations.leave,conversations.list,conversations.listConnectInvites,conversations.mark,conversations.members,conversations.open,conversations.rename,conversations.replies,conversations.setPurpose,conversations.setTopic,conversations.unarchive,conversations.canvases.create,conversations.externalInvitePermissions.set,conversations.requestSharedInvite.approve,conversations.requestSharedInvite.deny,conversations.requestSharedInvite.list,dialog.open,dnd.endDnd,dnd.endSnooze,dnd.info,dnd.setSnooze,dnd.teamInfo,emoji.list,files.comments.delete,files.completeUploadExternal,files.delete,files.getUploadURLExternal,files.info,files.list,files.revokePublicURL,files.sharedPublicURL,files.upload,files.remote.add,files.remote.info,files.remote.list,files.remote.remove,files.remote.share,files.remote.update,functions.completeError,functions.completeSuccess,functions.distributions.permissions.add,functions.distributions.permissions.list,functions.distributions.permissions.remove,functions.distributions.permissions.set,functions.workflows.steps.list,functions.workflows.steps.responses.export,groups.mark,migration.exchange,oauth.access,oauth.v2.access,oauth.v2.exchange,openid.connect.token,openid.connect.userInfo,pins.add,pins.list,pins.remove,reactions.add,reactions.get,reactions.list,reactions.remove,reminders.add,reminders.complete,reminders.delete,reminders.info,reminders.list,rtm.connect,rtm.start,search.all,search.files,search.messages,slackLists.access.delete,slackLists.access.set,slackLists.create,slackLists.download.get,slackLists.download.start,slackLists.items.create,slackLists.items.delete,slackLists.items.deleteMultiple,slackLists.items.info,slackLists.items.list,slackLists.items.update,slackLists.update,stars.add,stars.list,stars.remove,team.accessLogs,team.billableInfo,team.info,team.integrationLogs,team.billing.info,team.externalTeams.disconnect,team.externalTeams.list,team.preferences.list,team.profile.get,tooling.tokens.rotate,usergroups.create,usergroups.disable,usergroups.enable,usergroups.list,usergroups.update,usergroups.users.list,usergroups.users.update,users.conversations,users.deletePhoto,users.getPresence,users.identity,users.info,users.list,users.lookupByEmail,users.setActive,users.setPhoto,users.setPresence,users.discoverableContacts.lookup,users.profile.get,users.profile.set,views.open,views.publish,views.push,views.update,workflows.stepCompleted,workflows.stepFailed,workflows.updateStep,workflows.triggers.permissions.add,workflows.triggers.permissions.list,workflows.triggers.permissions.remove,workflows.triggers.permissions.set,im.list,im.mark,mpim.list,mpim.mark";
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
                // Salesforce channel related APIs
                "admin.conversations.linkObjects",
                "admin.conversations.unlinkObjects",
                "admin.conversations.createForObjects",
                // These next-generation platform related APIs work only on the automation platform.
                // Thus, we don't have short-term plans to add them to this Java SDK.
                "apps.auth.external.delete",
                "apps.auth.external.get",
                "apps.datastore.delete",
                "apps.datastore.get",
                "apps.datastore.put",
                "apps.datastore.query",
                "apps.datastore.update",
                "apps.activities.list",
                "apps.datastore.bulkDelete",
                "apps.datastore.bulkGet",
                "apps.datastore.bulkPut",
                "apps.datastore.count",
                "functions.workflows.steps.list",
                "functions.workflows.steps.responses.export",
                "functions.distributions.permissions.add",
                "functions.distributions.permissions.list",
                "functions.distributions.permissions.remove",
                "functions.distributions.permissions.set",
                "workflows.triggers.permissions.add",
                "workflows.triggers.permissions.list",
                "workflows.triggers.permissions.remove",
                "workflows.triggers.permissions.set",
                "admin.workflows.triggers.types.permissions.lookup",
                "admin.workflows.triggers.types.permissions.set",
                // TODO: admin.audit.anomaly.allow.*
                // The endpoints requires a "session" token
                "admin.audit.anomaly.allow.getItem",
                "admin.audit.anomaly.allow.updateItem",
                // TODO: Assistant's search
                "assistant.search.context"
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
