package com.github.seratch.jslack.api.methods.request.reminders;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RemindersListRequest {

    private String token;
}