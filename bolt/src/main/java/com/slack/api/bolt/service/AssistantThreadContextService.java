package com.slack.api.bolt.service;

import com.slack.api.model.assistant.AssistantThreadContext;

import java.util.Optional;

public interface AssistantThreadContextService {

    Optional<AssistantThreadContext> findCurrentContext(String channelId, String threadTs);

    void saveCurrentContext(String channelId, String threadTs, AssistantThreadContext context);

}
