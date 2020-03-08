package com.slack.api.app_backend.interactive_components.payload;

import com.google.gson.annotations.SerializedName;
import com.slack.api.model.Message;
import com.slack.api.model.block.composition.ConfirmationDialogObject;
import com.slack.api.model.block.composition.OptionObject;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.view.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * https://api.slack.com/messaging/interactivity/enabling
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlockActionPayload {

    public static final String TYPE = "block_actions";

    private final String type = TYPE;
    private Team team;
    private User user;
    private String apiAppId;
    private String token;
    private Container container;
    private String triggerId;
    private Channel channel;
    private Message message;
    private String responseUrl;
    private View view;
    private List<Action> actions;

    // TODO: app_unfurl
    // https://github.com/slackapi/bolt/blob/8f9245f9b9dce0771bb615b42192e7adb6228444/src/types/actions/block-action.ts#L154-L155

    @Data
    public static class Team {
        private String id;
        private String domain;
        private String enterpriseId;
        private String enterpriseName;
    }

    @Data
    public static class User {
        private String id;
        private String username;
        private String name;
        private String teamId;
    }

    @Data
    public static class Container {
        private String type;
        private String messageTs;
        private Integer attachmentId;
        private String channelId;
        private String text;
        @SerializedName("is_ephemeral")
        private boolean ephemeral;
        @SerializedName("is_app_unfurl")
        private boolean app_unfurl;
    }

    @Data
    public static class Channel {
        private String id;
        private String name;
    }

    @Data
    public static class Action {
        private String actionId;
        private String blockId;
        private Text text;
        private String value;
        private String type;
        private String actionTs;

        // common fields
        private PlainTextObject placeholder;
        private ConfirmationDialogObject confirm;

        // button
        private String url;

        // static_select
        private OptionObject initialOption;
        private SelectedOption selectedOption; // overflow

        // users_select
        private String selectedUser;
        private String initialUser;

        // conversations_select
        private String selectedConversation;
        private String initialConversation;

        // channels_select
        private String selectedChannel;
        private String initialChannel;

        // external_select
        private Integer minQueryLength;

        // datepicker
        private String selectedDate;
        private String initialDate;

        // multi_static_select
        // multi_external_select
        private List<OptionObject> initialOptions;
        private List<SelectedOption> selectedOptions;

        // multi_users_select
        private List<String> initialUsers;
        private List<String> selectedUsers;

        // multi_conversations_select
        private List<String> initialConversations;
        private List<String> selectedConversations;

        // multi_channels_select
        private List<String> initialChannels;
        private List<String> selectedChannels;

        @Data
        public static class Text {
            private String type;
            private String text;
            private boolean emoji;
        }

        @Data
        public static class SelectedOption {
            private PlainTextObject text;
            private String value;
        }
    }

}
