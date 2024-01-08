package test_locally.sample_json_generation;

import com.slack.api.model.event.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import util.ObjectInitializer;
import util.sample_json_generation.ObjectToJsonDumper;
import util.sample_json_generation.SampleObjects;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class RTMPayloadDumpTest {

    ObjectToJsonDumper dumper = new ObjectToJsonDumper("../json-logs/samples/rtm");

    @Ignore // stopped maintaining these JSON data on Jan 8, 2024
    @Test
    public void dumpAll() throws Exception {
        List<Event> payloads = Arrays.asList(
                new AccountChangedEvent(),
                new AppHomeOpenedEvent(),
                new AppMentionEvent(),
                new AppRateLimitedEvent(),
                new AppUninstalledEvent(),
                new BotAddedEvent(),
                new BotChangedEvent(),
                new ChannelArchiveEvent(),
                new ChannelCreatedEvent(),
                new ChannelDeletedEvent(),
                new ChannelHistoryChangedEvent(),
                new ChannelJoinedEvent(),
                new ChannelLeftEvent(),
                new ChannelMarkedEvent(),
                new ChannelRenameEvent(),
                new ChannelUnarchiveEvent(),
                new CommandsChangedEvent(),
                new DndUpdatedEvent(),
                new DndUpdatedUserEvent(),
                new EmailDomainChangedEvent(),
                buildEmojiChangedEvent(),
                new ErrorEvent(),
                buildExternalOrgMigrationFinishedEvent(),
                buildExternalOrgMigrationStartedEvent(),
                new FileChangeEvent(),
                new FileCommentAddedEvent(),
                new FileCommentDeletedEvent(),
                new FileCommentEditedEvent(),
                new FileCreatedEvent(),
                new FileDeletedEvent(),
                new FilePublicEvent(),
                new FileSharedEvent(),
                new FileUnsharedEvent(),
                new GoodbyeEvent(),
                new GridMigrationFinishedEvent(),
                new GridMigrationStartedEvent(),
                new GroupArchiveEvent(),
                new GroupCloseEvent(),
                new GroupDeletedEvent(),
                new GroupHistoryChangedEvent(),
                new GroupJoinedEvent(),
                new GroupLeftEvent(),
                new GroupMarkedEvent(),
                new GroupOpenEvent(),
                new GroupRenameEvent(),
                new GroupUnarchiveEvent(),
                new HelloEvent(),
                new ImCloseEvent(),
                new ImCreatedEvent(),
                new ImHistoryChangedEvent(),
                new ImMarkedEvent(),
                new ImOpenEvent(),
                buildLinkSharedEvent(),
                new ManualPresenceChangeEvent(),
                new MemberJoinedChannelEvent(),
                new MemberLeftChannelEvent(),
                buildMessageEvent(),
                buildPinAddedEvent(),
                buildPinRemovedEvent(),
                new PrefChangeEvent(),
                buildPresenceChangeEvent(),
                new ReactionAddedEvent(),
                new ReactionRemovedEvent(),
                new ReconnectUrlEvent(),
                buildResourcesAddedEvent(),
                buildResourcesRemovedEvent(),
                buildScopeDeniedEvent(),
                buildScopeGrantedEvent(),
                buildStarAddedEvent(),
                buildStarRemovedEvent(),
                buildSubteamCreatedEvent(),
                buildSubteamMembersChangedEvent(),
                new SubteamSelfAddedEvent(),
                new SubteamSelfRemovedEvent(),
                buildSubteamUpdatedEvent(),
                new TeamDomainChangeEvent(),
                new TeamJoinEvent(),
                new TeamMigrationStartedEvent(),
                buildTeamPlanChangeEvent(),
                new TeamPrefChangeEvent(),
                buildTeamProfileChangeEvent(),
                buildTeamProfileDeleteEvent(),
                buildTeamProfileReorderEvent(),
                new TeamRenameEvent(),
                buildTokensRevokedEvent(),
                new UserChangeEvent(),
                buildUserResourceDeniedEvent(),
                buildUserResourceGrantedEvent(),
                new UserResourceRemovedEvent(),
                new UserTypingEvent()
        );
        for (Event payload : payloads) {
            ObjectInitializer.initProperties(payload);
            dumper.dump(payload.getClass().getSimpleName(), payload);
        }
    }

    private UserResourceGrantedEvent buildUserResourceGrantedEvent() {
        UserResourceGrantedEvent event = new UserResourceGrantedEvent();
        event.setScopes(Arrays.asList(""));
        return event;
    }

    private UserResourceDeniedEvent buildUserResourceDeniedEvent() {
        UserResourceDeniedEvent event = new UserResourceDeniedEvent();
        event.setScopes(Arrays.asList(""));
        return event;
    }

    private TokensRevokedEvent buildTokensRevokedEvent() {
        TokensRevokedEvent event = new TokensRevokedEvent();
        event.setTokens(new TokensRevokedEvent.Tokens());
        event.getTokens().setOauth(Arrays.asList(""));
        event.getTokens().setBot(Arrays.asList(""));
        return event;
    }

    private TeamProfileReorderEvent buildTeamProfileReorderEvent() {
        TeamProfileReorderEvent event = new TeamProfileReorderEvent();
        event.setProfile(new TeamProfileReorderEvent.Profile());
        event.getProfile().setFields(Arrays.asList(new TeamProfileReorderEvent.Field()));
        return event;
    }

    private TeamProfileDeleteEvent buildTeamProfileDeleteEvent() {
        TeamProfileDeleteEvent event = new TeamProfileDeleteEvent();
        event.setProfile(new TeamProfileDeleteEvent.Profile());
        event.getProfile().setFields(Arrays.asList(new TeamProfileDeleteEvent.Field()));
        return event;
    }

    private TeamProfileChangeEvent buildTeamProfileChangeEvent() {
        TeamProfileChangeEvent event = new TeamProfileChangeEvent();
        event.setProfile(new TeamProfileChangeEvent.Profile());
        event.getProfile().setFields(Arrays.asList(new TeamProfileChangeEvent.Field()));
        return event;
    }

    private TeamPlanChangeEvent buildTeamPlanChangeEvent() {
        TeamPlanChangeEvent event = new TeamPlanChangeEvent();
        event.setPaidFeatures(Arrays.asList(""));
        return event;
    }

    private SubteamUpdatedEvent buildSubteamUpdatedEvent() {
        SubteamUpdatedEvent event = new SubteamUpdatedEvent();
        event.setSubteam(new SubteamUpdatedEvent.Subteam());
        event.getSubteam().setPrefs(new SubteamUpdatedEvent.Prefs());
        event.getSubteam().getPrefs().setChannels(Arrays.asList(""));
        event.getSubteam().getPrefs().setGroups(Arrays.asList(""));
        return event;
    }

    private SubteamMembersChangedEvent buildSubteamMembersChangedEvent() {
        SubteamMembersChangedEvent event = new SubteamMembersChangedEvent();
        event.setAddedUsers(Arrays.asList(""));
        event.setRemovedUsers(Arrays.asList(""));
        return event;
    }

    private SubteamCreatedEvent buildSubteamCreatedEvent() {
        SubteamCreatedEvent event = new SubteamCreatedEvent();
        event.setSubteam(new SubteamCreatedEvent.Subteam());
        event.getSubteam().setPrefs(new SubteamCreatedEvent.Prefs());
        event.getSubteam().getPrefs().setChannels(Arrays.asList(""));
        event.getSubteam().getPrefs().setGroups(Arrays.asList(""));
        return event;
    }

    private StarRemovedEvent buildStarRemovedEvent() {
        StarRemovedEvent event = new StarRemovedEvent();
        event.setItem(new StarRemovedEvent.Item());
        event.getItem().setMessage(new StarRemovedEvent.Message());
        event.getItem().getMessage().setPinnedTo(Arrays.asList(""));
        event.getItem().getMessage().setBlocks(SampleObjects.Blocks);
        event.getItem().getMessage().setAttachments(SampleObjects.Attachments);
        return event;
    }

    private StarAddedEvent buildStarAddedEvent() {
        StarAddedEvent event = new StarAddedEvent();
        event.setItem(new StarAddedEvent.Item());
        event.getItem().setMessage(new StarAddedEvent.Message());
        event.getItem().getMessage().setPinnedTo(Arrays.asList(""));
        event.getItem().getMessage().setBlocks(SampleObjects.Blocks);
        event.getItem().getMessage().setAttachments(SampleObjects.Attachments);
        return event;
    }

    private ScopeGrantedEvent buildScopeGrantedEvent() {
        ScopeGrantedEvent event = new ScopeGrantedEvent();
        event.setScopes(Arrays.asList(""));
        return event;
    }

    private ScopeDeniedEvent buildScopeDeniedEvent() {
        ScopeDeniedEvent event = new ScopeDeniedEvent();
        event.setScopes(Arrays.asList(""));
        return event;
    }

    private ResourcesRemovedEvent buildResourcesRemovedEvent() {
        ResourcesRemovedEvent event = new ResourcesRemovedEvent();
        event.setResources(Arrays.asList(new ResourcesRemovedEvent.ResourceItem()));
        event.getResources().get(0).setScopes(Arrays.asList(""));
        return event;
    }

    private ResourcesAddedEvent buildResourcesAddedEvent() {
        ResourcesAddedEvent event = new ResourcesAddedEvent();
        event.setResources(Arrays.asList(new ResourcesAddedEvent.ResourceItem()));
        event.getResources().get(0).setScopes(Arrays.asList(""));
        return event;
    }

    private PresenceChangeEvent buildPresenceChangeEvent() {
        PresenceChangeEvent event = new PresenceChangeEvent();
        event.setUsers(Arrays.asList(""));
        return event;
    }

    private PinRemovedEvent buildPinRemovedEvent() {
        PinRemovedEvent event = new PinRemovedEvent();
        event.setItem(new PinRemovedEvent.Item());
        event.getItem().setMessage(new PinRemovedEvent.Message());
        event.getItem().getMessage().setAttachments(SampleObjects.Attachments);
        event.getItem().getMessage().setBlocks(SampleObjects.Blocks);
        event.getItem().getMessage().setPinnedTo(Arrays.asList(""));
        return event;
    }

    private PinAddedEvent buildPinAddedEvent() {
        PinAddedEvent event = new PinAddedEvent();
        event.setItem(new PinAddedEvent.Item());
        event.getItem().setMessage(new PinAddedEvent.Message());
        event.getItem().getMessage().setAttachments(SampleObjects.Attachments);
        event.getItem().getMessage().setBlocks(SampleObjects.Blocks);
        event.getItem().getMessage().setPinnedTo(Arrays.asList(""));
        return event;
    }

    private MessageEvent buildMessageEvent() {
        MessageEvent event = new MessageEvent();
        event.setBlocks(SampleObjects.Blocks);
        event.setAttachments(SampleObjects.Attachments);
        return event;
    }

    private LinkSharedEvent buildLinkSharedEvent() {
        LinkSharedEvent event = new LinkSharedEvent();
        event.setLinks(Arrays.asList(new LinkSharedEvent.Link()));
        return event;
    }

    private EmojiChangedEvent buildEmojiChangedEvent() {
        EmojiChangedEvent event = new EmojiChangedEvent();
        event.setNames(Arrays.asList(""));
        return event;
    }

    private ExternalOrgMigrationFinishedEvent buildExternalOrgMigrationFinishedEvent() {
        ExternalOrgMigrationFinishedEvent event = new ExternalOrgMigrationFinishedEvent();
        event.setTeam(new ExternalOrgMigrationFinishedEvent.Team());
        return event;
    }

    private ExternalOrgMigrationStartedEvent buildExternalOrgMigrationStartedEvent() {
        ExternalOrgMigrationStartedEvent event = new ExternalOrgMigrationStartedEvent();
        event.setTeam(new ExternalOrgMigrationStartedEvent.Team());
        return event;
    }

}
