package com.slack.api.methods.request.admin.conversations;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.util.json.GsonFactory;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * https://api.slack.com/methods/admin.conversations.setConversationPrefs
 */
@Data
@Builder
public class AdminConversationsSetConversationPrefsRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * The channel to set the prefs for.
     */
    private String channelId;

    /**
     * The prefs for this channel in a stringified JSON format.
     * {'who_can_post':'type:admin,user:U1234,subteam:S1234'}
     */
    private String prefsAsString;
    private Prefs prefs;

    @Data
    public static class Pref {
        private List<String> types = new ArrayList<>();
        private List<String> users = new ArrayList<>();
        private List<String> subteams = new ArrayList<>();

        public String toValue() {
            List<String> elements = new ArrayList<>();
            if (getTypes() != null) {
                for (String type : getTypes()) {
                    elements.add("type:" + type);
                }
            }
            if (getUsers() != null) {
                for (String user : getUsers()) {
                    elements.add("user:" + user);
                }
            }
            if (getSubteams() != null) {
                for (String subteam : getSubteams()) {
                    elements.add("subteam:" + subteam);
                }
            }
            return elements.stream().collect(Collectors.joining(","));
        }
    }

    @Data
    public static class Prefs {
        private Pref whoCanPost = new Pref();
        private Pref canThread = new Pref();

        public String toValue() {
            Map<String, String> value = new HashMap<>();
            value.put("who_can_post", getWhoCanPost().toValue());
            value.put("can_thread", getCanThread().toValue());
            return GsonFactory.createSnakeCase().toJson(value);
        }
    }

}
