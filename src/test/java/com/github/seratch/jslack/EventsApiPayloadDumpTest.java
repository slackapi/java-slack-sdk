package com.github.seratch.jslack;

import com.github.seratch.jslack.app_backend.events.payload.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import sample_json_generation.ObjectToJsonDumper;
import util.ObjectInitializer;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class EventsApiPayloadDumpTest {

    ObjectToJsonDumper dumper = new ObjectToJsonDumper("./json-logs/samples/events");

    @Test
    public void dumpAll() throws Exception {
        List<EventsApiPayload<?>> payloads = Arrays.asList(
                new AppMentionPayload(),
                new AppRateLimitedPayload(),
                new AppUninstalledPayload(),
                new ChannelArchivePayload(),
                new ChannelCreatedPayload(),
                new ChannelDeletedPayload(),
                new ChannelHistoryChangedPayload(),
                new ChannelLeftPayload(),
                new ChannelRenamePayload(),
                new ChannelUnarchivePayload(),
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
                new LinkSharedPayload(),
                new MemberJoinedChannelPayload(),
                new MemberLeftChannelPayload(),
                new MessagePayload(),
                new PinAddedPayload(),
                new PinRemovedPayload(),
                new ReactionAddedPayload(),
                new ReactionRemovedPayload(),
                new ResourcesAddedPayload(),
                new ResourcesRemovedPayload(),
                new ScopeDeniedPayload(),
                new ScopeGrantedPayload(),
                new StarAddedPayload(),
                new StarRemovedPayload(),
                new SubteamCreatedPayload(),
                new SubteamMembersChangedPayload(),
                new SubteamSelfAddedPayload(),
                new SubteamSelfRemovedPayload(),
                new SubteamUpdatedPayload(),
                new TeamDomainChangePayload(),
                new TeamJoinPayload(),
                new TeamRenamePayload(),
                new TokensRevokedPayload(),
                new UserChangePayload(),
                new UserResourceDeniedPayload(),
                new UserResourceGrantedPayload(),
                new UserResourceRemovedPayload()
        );
        for (EventsApiPayload<?> payload : payloads) {
            ObjectInitializer.initProperties(payload);
            dumper.dump(payload.getClass().getSimpleName(), payload);
        }
    }
}
