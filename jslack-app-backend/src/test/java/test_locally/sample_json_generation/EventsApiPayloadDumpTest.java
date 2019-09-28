package test_locally.sample_json_generation;

import com.github.seratch.jslack.api.model.Reaction;
import com.github.seratch.jslack.api.model.event.*;
import com.github.seratch.jslack.app_backend.events.payload.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import util.sample_json_generation.ObjectToJsonDumper;
import util.sample_json_generation.SampleObjects;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static util.ObjectInitializer.initProperties;

@Slf4j
public class EventsApiPayloadDumpTest {

    ObjectToJsonDumper dumper = new ObjectToJsonDumper("../json-logs/samples/events");

    @Test
    public void dumpAll() throws Exception {
        List<EventsApiPayload<?>> payloads = Arrays.asList(
                new AppHomeOpenedPayload(),
                new AppMentionPayload(),
                new AppRequestedPayload(),
                new AppRateLimitedPayload(),
                new AppUninstalledPayload(),
                new ChannelArchivePayload(),
                new ChannelCreatedPayload(),
                new ChannelDeletedPayload(),
                new ChannelHistoryChangedPayload(),
                new ChannelLeftPayload(),
                new ChannelRenamePayload(),
                new ChannelSharedPayload(),
                new ChannelUnarchivePayload(),
                new ChannelUnsharedPayload(),
                new DndUpdatedPayload(),
                new DndUpdatedUserPayload(),
                new EmailDomainChangedPayload(),
                new EmojiChangedPayload(),
                new FileChangePayload(),
                new FileCreatedPayload(),
                new FileDeletedPayload(),
                new FilePublicPayload(),
                new FileSharedPayload(),
                new FileUnsharedPayload(),
                new GoodbyePayload(),
                new GridMigrationFinishedPayload(),
                new GridMigrationStartedPayload(),
                new GroupArchivePayload(),
                new GroupClosePayload(),
                new GroupDeletedPayload(),
                new GroupHistoryChangedPayload(),
                new GroupLeftPayload(),
                new GroupOpenPayload(),
                new GroupRenamePayload(),
                new GroupUnarchivePayload(),
                new ImClosePayload(),
                new ImCreatedPayload(),
                new ImHistoryChangedPayload(),
                new ImOpenPayload(),
                buildLinkSharedPayload(),
                new MemberJoinedChannelPayload(),
                new MemberLeftChannelPayload(),
                buildMessagePayload(),
                buildPinAddedPayload(),
                buildPinRemovedPayload(),
                new ReactionAddedPayload(),
                new ReactionRemovedPayload(),
                buildResourcesAddedPayload(),
                buildResourcesRemovedPayload(),
                buildScopeDeniedPayload(),
                buildScopeGrantedPayload(),
                buildStarAddedPayload(),
                buildStarRemovedPayload(),
                buildSubteamCreatedPayload(),
                buildSubteamMembersChangedPayload(),
                new SubteamSelfAddedPayload(),
                new SubteamSelfRemovedPayload(),
                buildSubteamUpdatedPayload(),
                new TeamDomainChangePayload(),
                new TeamJoinPayload(),
                new TeamRenamePayload(),
                buildTokensRevokedPayload(),
                new UserChangePayload(),
                buildUserResourceDeniedPayload(),
                buildUserResourceGrantedPayload(),
                new UserResourceRemovedPayload()
        );
        for (EventsApiPayload<?> payload : payloads) {
            try {
                Field authedUsers = payload.getClass().getDeclaredField("authedUsers");
                if (authedUsers != null) {
                    authedUsers.setAccessible(true);
                    authedUsers.set(payload, Arrays.asList(""));
                }
            } catch (Exception e) {
            }
            try {
                Field authedTeams = payload.getClass().getDeclaredField("authedTeams");
                if (authedTeams != null) {
                    authedTeams.setAccessible(true);
                    authedTeams.set(payload, Arrays.asList(""));
                }
            } catch (Exception e) {
            }
            initProperties(payload);
            dumper.dump(payload.getClass().getSimpleName(), payload);
        }
    }

    private UserResourceGrantedPayload buildUserResourceGrantedPayload() {
        UserResourceGrantedPayload payload = new UserResourceGrantedPayload();
        payload.setEvent(new UserResourceGrantedEvent());
        payload.getEvent().setScopes(Arrays.asList(""));
        return payload;
    }

    private UserResourceDeniedPayload buildUserResourceDeniedPayload() {
        UserResourceDeniedPayload payload = new UserResourceDeniedPayload();
        payload.setEvent(new UserResourceDeniedEvent());
        payload.getEvent().setScopes(Arrays.asList(""));
        return payload;
    }

    private TokensRevokedPayload buildTokensRevokedPayload() {
        TokensRevokedPayload payload = new TokensRevokedPayload();
        payload.setEvent(new TokensRevokedEvent());
        payload.getEvent().setTokens(new TokensRevokedEvent.Tokens());
        payload.getEvent().getTokens().setBot(Arrays.asList(""));
        payload.getEvent().getTokens().setOauth(Arrays.asList(""));
        return payload;
    }

    private SubteamUpdatedPayload buildSubteamUpdatedPayload() {
        SubteamUpdatedPayload payload = new SubteamUpdatedPayload();
        payload.setEvent(new SubteamUpdatedEvent());
        payload.getEvent().setSubteam(new SubteamUpdatedEvent.Subteam());
        payload.getEvent().getSubteam().setUsers(Arrays.asList(""));
        payload.getEvent().getSubteam().setPrefs(new SubteamUpdatedEvent.Prefs());
        payload.getEvent().getSubteam().getPrefs().setChannels(Arrays.asList(""));
        payload.getEvent().getSubteam().getPrefs().setGroups(Arrays.asList(""));
        return payload;
    }

    private SubteamMembersChangedPayload buildSubteamMembersChangedPayload() {
        SubteamMembersChangedPayload payload = new SubteamMembersChangedPayload();
        payload.setEvent(new SubteamMembersChangedEvent());
        payload.getEvent().setAddedUsers(Arrays.asList(""));
        payload.getEvent().setRemovedUsers(Arrays.asList(""));
        return payload;
    }

    private SubteamCreatedPayload buildSubteamCreatedPayload() {
        SubteamCreatedPayload payload = new SubteamCreatedPayload();
        payload.setEvent(new SubteamCreatedEvent());
        payload.getEvent().setSubteam(new SubteamCreatedEvent.Subteam());
        payload.getEvent().getSubteam().setPrefs(new SubteamCreatedEvent.Prefs());
        payload.getEvent().getSubteam().getPrefs().setChannels(Arrays.asList(""));
        payload.getEvent().getSubteam().getPrefs().setGroups(Arrays.asList(""));
        return payload;
    }

    private StarRemovedPayload buildStarRemovedPayload() {
        StarRemovedPayload payload = new StarRemovedPayload();
        payload.setEvent(new StarRemovedEvent());
        payload.getEvent().setItem(new StarRemovedEvent.Item());
        payload.getEvent().getItem().setMessage(new StarRemovedEvent.Message());
        payload.getEvent().getItem().getMessage().setAttachments(SampleObjects.Attachments);
        payload.getEvent().getItem().getMessage().setBlocks(SampleObjects.Blocks);
        payload.getEvent().getItem().getMessage().setPinnedTo(Arrays.asList(""));
        return payload;
    }

    private StarAddedPayload buildStarAddedPayload() {
        StarAddedPayload payload = new StarAddedPayload();
        payload.setEvent(new StarAddedEvent());
        payload.getEvent().setItem(new StarAddedEvent.Item());
        payload.getEvent().getItem().setMessage(new StarAddedEvent.Message());
        payload.getEvent().getItem().getMessage().setAttachments(SampleObjects.Attachments);
        payload.getEvent().getItem().getMessage().setBlocks(SampleObjects.Blocks);
        payload.getEvent().getItem().getMessage().setPinnedTo(Arrays.asList(""));
        return payload;
    }

    private ScopeGrantedPayload buildScopeGrantedPayload() {
        ScopeGrantedPayload payload = new ScopeGrantedPayload();
        payload.setEvent(new ScopeGrantedEvent());
        payload.getEvent().setScopes(Arrays.asList(""));
        return payload;
    }

    private ScopeDeniedPayload buildScopeDeniedPayload() {
        ScopeDeniedPayload payload = new ScopeDeniedPayload();
        payload.setEvent(new ScopeDeniedEvent());
        payload.getEvent().setScopes(Arrays.asList(""));
        return payload;
    }

    private ResourcesRemovedPayload buildResourcesRemovedPayload() {
        ResourcesRemovedPayload payload = new ResourcesRemovedPayload();
        payload.setEvent(new ResourcesRemovedEvent());
        ResourcesRemovedEvent.ResourceItem resource = new ResourcesRemovedEvent.ResourceItem();
        resource.setResource(new ResourcesRemovedEvent.Resource());
        resource.setScopes(Arrays.asList(""));
        payload.getEvent().setResources(Arrays.asList(initProperties(resource)));
        return payload;
    }

    private ResourcesAddedPayload buildResourcesAddedPayload() {
        ResourcesAddedPayload payload = new ResourcesAddedPayload();
        payload.setEvent(new ResourcesAddedEvent());
        ResourcesAddedEvent.ResourceItem resource = new ResourcesAddedEvent.ResourceItem();
        resource.setResource(new ResourcesAddedEvent.Resource());
        resource.setScopes(Arrays.asList(""));
        payload.getEvent().setResources(Arrays.asList(initProperties(resource)));
        return payload;
    }

    private PinRemovedPayload buildPinRemovedPayload() {
        PinRemovedPayload payload = new PinRemovedPayload();
        payload.setEvent(new PinRemovedEvent());
        payload.getEvent().setItem(new PinRemovedEvent.Item());
        PinRemovedEvent.Message message = initProperties(new PinRemovedEvent.Message());
        message.setPinnedTo(Arrays.asList(""));
        message.setAttachments(SampleObjects.Attachments);
        message.setBlocks(SampleObjects.Blocks);
        payload.getEvent().getItem().setMessage(initProperties(message));
        return payload;
    }

    private PinAddedPayload buildPinAddedPayload() {
        PinAddedPayload payload = new PinAddedPayload();
        payload.setEvent(new PinAddedEvent());
        payload.getEvent().setItem(new PinAddedEvent.Item());
        PinAddedEvent.Message message = initProperties(new PinAddedEvent.Message());
        message.setPinnedTo(Arrays.asList(""));
        message.setAttachments(SampleObjects.Attachments);
        message.setBlocks(SampleObjects.Blocks);
        payload.getEvent().getItem().setMessage(initProperties(message));
        return payload;
    }

    private LinkSharedPayload buildLinkSharedPayload() {
        LinkSharedPayload payload = new LinkSharedPayload();
        payload.setEvent(new LinkSharedEvent());
        payload.getEvent().setLinks(Arrays.asList(initProperties(new LinkSharedEvent.Link())));
        return payload;
    }

    private MessagePayload buildMessagePayload() {
        MessagePayload payload = new MessagePayload();
        payload.setEvent(new MessageEvent());
        payload.getEvent().setAttachments(SampleObjects.Attachments);
        payload.getEvent().setBlocks(SampleObjects.Blocks);
        payload.getEvent().setPinnedTo(Arrays.asList(""));
        payload.getEvent().setReactions(Arrays.asList(initProperties(Reaction.builder().users(Arrays.asList("")).build())));
        return payload;
    }
}
