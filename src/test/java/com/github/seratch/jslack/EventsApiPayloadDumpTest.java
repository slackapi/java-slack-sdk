package com.github.seratch.jslack;

import com.github.seratch.jslack.app_backend.events.payload.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import testing.json.EventsPayloadDumper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class EventsApiPayloadDumpTest {

    EventsPayloadDumper dumper = new EventsPayloadDumper("./json-logs/samples/events");

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
            initProperties(payload);
            dumper.dump(payload.getClass().getSimpleName(), payload);
        }
    }

    private void initProperties(Object obj) throws Exception {
        for (Field field : obj.getClass().getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) ||
                    Modifier.isFinal(field.getModifiers()) ||
                    Modifier.isAbstract(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            Class<?> type = field.getType();
            if (type.equals(String.class)) {
                field.set(obj, "");
            } else if (type.equals(Integer.class)) {
                field.set(obj, 123);
            } else if (type.equals(Long.class)) {
                field.set(obj, 123L);
            } else if (type.equals(Double.class)) {
                field.set(obj, 12.3D);
            } else if (type.equals(Boolean.class)) {
                field.set(obj, false);
            } else {
                for (Constructor<?> constructor : type.getDeclaredConstructors()) {
                    if (constructor.getParameterCount() == 0) {
                        Object childObj = constructor.newInstance();
                        initProperties(childObj);
                        field.set(obj, childObj);
                        continue;
                    }
                }
                log.info("Skipped a field which doesn't have non arg constructor");
            }
        }
    }

}
