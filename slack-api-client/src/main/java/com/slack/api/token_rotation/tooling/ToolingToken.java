package com.slack.api.token_rotation.tooling;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToolingToken {
    private String teamId;
    private String userId;
    private String refreshToken; // xoxe-...
    private String accessToken; // xoxe.xoxp-...
    private Integer expireAt; // epoch time (seconds)

    public boolean isExpired() {
        if (this.getExpireAt() == null) return true;
        return this.getExpireAt() < System.currentTimeMillis() / 1000;
    }
}
