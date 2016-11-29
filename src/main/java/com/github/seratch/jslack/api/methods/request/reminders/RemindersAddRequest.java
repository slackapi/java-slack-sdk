package com.github.seratch.jslack.api.methods.request.reminders;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RemindersAddRequest implements SlackApiRequest {

    private String token;
    private String text;
    private String time;
    private String user;
}