package com.github.seratch.jslack.api.methods.request.reminders;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RemindersAddRequest {

    private String token;
    private String text;
    private String time;
    private String user;
}