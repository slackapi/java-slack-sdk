package com.github.seratch.jslack.app_backend.interactive_messages.payload;

import com.github.seratch.jslack.api.model.Message;
import com.github.seratch.jslack.api.model.block.composition.ConfirmationDialogObject;
import com.github.seratch.jslack.api.model.block.composition.OptionObject;
import com.github.seratch.jslack.api.model.block.composition.PlainTextObject;
import com.github.seratch.jslack.api.model.view.View;
import com.google.gson.annotations.SerializedName;
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
        private String enterpriseId; // https://github.com/slackapi/bolt/blob/8f9245f9b9dce0771bb615b42192e7adb6228444/src/types/actions/block-action.ts#L127-L128
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

        // Add more properties found here:
        // https://github.com/slackapi/bolt/blob/8f9245f9b9dce0771bb615b42192e7adb6228444/src/types/actions/block-action.ts#L127-L128

        // button
        private String url;
        private ConfirmationDialogObject confirm;

        // static_select
        private OptionObject initialOption;
        private PlainTextObject placeholder;
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
        // private Option initialOption;
        private Integer minQueryLength;

        // datepicker
        private String selectedDate;
        private String initialDate;

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
