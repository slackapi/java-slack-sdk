package com.github.seratch.jslack;

import com.github.seratch.jslack.api.model.event.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import sample_json_generation.ObjectToJsonDumper;
import util.ObjectInitializer;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class RTMPayloadDumpTest {

    ObjectToJsonDumper dumper = new ObjectToJsonDumper("./json-logs/samples/rtm");

    @Test
    public void dumpAll() throws Exception {
        List<Event> payloads = Arrays.asList(
                new AccountChangedEvent(),
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
                new EmojiChangedEvent(),
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
                new LinkSharedEvent(),
                new ManualPresenceChangeEvent(),
                new MemberJoinedChannelEvent(),
                new MemberLeftChannelEvent(),
                new MessageEvent(),
                new PinAddedEvent(),
                new PinRemovedEvent(),
                new PrefChangeEvent(),
                new PresenceChangeEvent(),
                new ReactionAddedEvent(),
                new ReactionRemovedEvent(),
                new ReconnectUrlEvent(),
                new ResourcesAddedEvent(),
                new ResourcesRemovedEvent(),
                new ScopeDeniedEvent(),
                new ScopeGrantedEvent(),
                new StarAddedEvent(),
                new StarRemovedEvent(),
                new SubteamCreatedEvent(),
                new SubteamMembersChangedEvent(),
                new SubteamSelfAddedEvent(),
                new SubteamSelfRemovedEvent(),
                new SubteamUpdatedEvent(),
                new TeamDomainChangeEvent(),
                new TeamJoinEvent(),
                new TeamMigrationStartedEvent(),
                new TeamPlanChangeEvent(),
                new TeamPrefChangeEvent(),
                new TeamProfileChangeEvent(),
                new TeamProfileDeleteEvent(),
                new TeamProfileReorderEvent(),
                new TeamRenameEvent(),
                new TokensRevokedEvent(),
                new UserChangeEvent(),
                new UserResourceDeniedEvent(),
                new UserResourceGrantedEvent(),
                new UserResourceRemovedEvent(),
                new UserTypingEvent()
        );
        for (Event payload : payloads) {
            ObjectInitializer.initProperties(payload);
            dumper.dump(payload.getClass().getSimpleName(), payload);
        }
    }

}
