package com.slack.api.model.assistant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuggestedPrompt {
    private String title;
    private String message;

    public SuggestedPrompt(String title, String message) {
        setTitle(title);
        setMessage(message);
    }

    public SuggestedPrompt(String message) {
        setTitle(message);
        setMessage(message);
    }

    public static SuggestedPrompt create(String message) {
        return new SuggestedPrompt(message);
    }
}
