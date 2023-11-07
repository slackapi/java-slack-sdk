package com.slack.api.token_rotation.tooling;

import java.util.Optional;

public interface ToolingTokenStore {

    void save(ToolingToken token);

    Optional<ToolingToken> find(String teamId, String userId);

}
