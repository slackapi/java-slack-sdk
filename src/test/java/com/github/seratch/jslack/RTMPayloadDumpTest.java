package com.github.seratch.jslack;

import com.github.seratch.jslack.api.model.event.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import testing.json.ObjectToJsonDumper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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
