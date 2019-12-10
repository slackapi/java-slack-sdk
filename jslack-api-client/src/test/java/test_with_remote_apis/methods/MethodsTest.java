package test_with_remote_apis.methods;

import com.github.seratch.jslack.api.methods.Methods;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

public class MethodsTest {

    @Test
    public void verifyTheCoverage() {
        // https://api.slack.com/methods
        // var methodNames = [].slice.call(document.getElementsByClassName('bold')).map(e => e.text);console.log(methodNames.toString());console.log(methodNames.length);
        String methods = "admin.apps.approve,admin.apps.restrict,admin.apps.requests.list,admin.inviteRequests.approve,admin.inviteRequests.deny,admin.inviteRequests.list,admin.inviteRequests.approved.list,admin.inviteRequests.denied.list,admin.teams.admins.list,admin.teams.create,admin.teams.list,admin.teams.owners.list,admin.teams.settings.setDescription,admin.teams.settings.setIcon,admin.teams.settings.setName,admin.users.assign,admin.users.invite,admin.users.remove,admin.users.setAdmin,admin.users.setOwner,admin.users.setRegular,admin.users.session.reset,api.test,apps.permissions.info,apps.permissions.request,apps.permissions.resources.list,apps.permissions.scopes.list,apps.permissions.users.list,apps.permissions.users.request,apps.uninstall,auth.revoke,auth.test,bots.info,channels.archive,channels.create,channels.history,channels.info,channels.invite,channels.join,channels.kick,channels.leave,channels.list,channels.mark,channels.rename,channels.replies,channels.setPurpose,channels.setTopic,channels.unarchive,chat.delete,chat.deleteScheduledMessage,chat.getPermalink,chat.meMessage,chat.postEphemeral,chat.postMessage,chat.scheduleMessage,chat.unfurl,chat.update,chat.scheduledMessages.list,conversations.archive,conversations.close,conversations.create,conversations.history,conversations.info,conversations.invite,conversations.join,conversations.kick,conversations.leave,conversations.list,conversations.members,conversations.open,conversations.rename,conversations.replies,conversations.setPurpose,conversations.setTopic,conversations.unarchive,dialog.open,dnd.endDnd,dnd.endSnooze,dnd.info,dnd.setSnooze,dnd.teamInfo,emoji.list,files.comments.delete,files.delete,files.info,files.list,files.revokePublicURL,files.sharedPublicURL,files.upload,files.remote.add,files.remote.info,files.remote.list,files.remote.remove,files.remote.share,files.remote.update,groups.archive,groups.create,groups.createChild,groups.history,groups.info,groups.invite,groups.kick,groups.leave,groups.list,groups.mark,groups.open,groups.rename,groups.replies,groups.setPurpose,groups.setTopic,groups.unarchive,im.close,im.history,im.list,im.mark,im.open,im.replies,migration.exchange,mpim.close,mpim.history,mpim.list,mpim.mark,mpim.open,mpim.replies,oauth.access,oauth.token,oauth.v2.access,pins.add,pins.list,pins.remove,reactions.add,reactions.get,reactions.list,reactions.remove,reminders.add,reminders.complete,reminders.delete,reminders.info,reminders.list,rtm.connect,rtm.start,search.all,search.files,search.messages,stars.add,stars.list,stars.remove,team.accessLogs,team.billableInfo,team.info,team.integrationLogs,team.profile.get,usergroups.create,usergroups.disable,usergroups.enable,usergroups.list,usergroups.update,usergroups.users.list,usergroups.users.update,users.conversations,users.deletePhoto,users.getPresence,users.identity,users.info,users.list,users.lookupByEmail,users.setActive,users.setPhoto,users.setPresence,users.profile.get,users.profile.set,views.open,views.publish,views.push,views.update";
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
        for (String method : methods.split(",")) {
            if (!existingMethods.contains(method)) {
                fail(method + " is not supported yet!");
            }
        }

    }
}
