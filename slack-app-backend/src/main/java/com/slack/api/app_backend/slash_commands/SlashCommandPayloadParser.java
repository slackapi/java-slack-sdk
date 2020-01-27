package com.slack.api.app_backend.slash_commands;

import com.slack.api.app_backend.slash_commands.payload.SlashCommandPayload;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Slf4j
public class SlashCommandPayloadParser {

    public SlashCommandPayload parse(String requestBody) {
        if (requestBody == null) {
            return null;
        }
        SlashCommandPayload payload = new SlashCommandPayload();
        String[] pairs = requestBody.split("\\&");
        for (String pair : pairs) {
            String[] fields = pair.split("=");
            if (fields.length == 2) {
                try {
                    String name = URLDecoder.decode(fields[0].trim().replaceAll("\\n+", ""), "UTF-8");
                    String value = URLDecoder.decode(fields[1], "UTF-8");
                    switch (name) {
                        case "token":
                            payload.setToken(value);
                            break;
                        case "team_id":
                            payload.setTeamId(value);
                            break;
                        case "team_domain":
                            payload.setTeamDomain(value);
                            break;
                        case "enterprise_id":
                            payload.setEnterpriseId(value);
                            break;
                        case "enterprise_name":
                            payload.setEnterpriseName(value);
                            break;
                        case "channel_id":
                            payload.setChannelId(value);
                            break;
                        case "channel_name":
                            payload.setChannelName(value);
                            break;
                        case "user_id":
                            payload.setUserId(value);
                            break;
                        case "user_name":
                            payload.setUserName(value);
                            break;
                        case "command":
                            payload.setCommand(value);
                            break;
                        case "text":
                            payload.setText(value);
                            break;
                        case "response_url":
                            payload.setResponseUrl(value);
                            break;
                        case "trigger_id":
                            payload.setTriggerId(value);
                            break;
                        default:
                    }
                } catch (UnsupportedEncodingException e) {
                    log.error("Failed to decode URL-encoded string values - {}", e.getMessage(), e);
                }
            }
        }
        return payload;
    }
}
