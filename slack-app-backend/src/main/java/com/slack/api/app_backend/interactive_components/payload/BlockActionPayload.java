package com.slack.api.app_backend.interactive_components.payload;

import com.google.gson.annotations.SerializedName;
import com.slack.api.model.Message;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.ConfirmationDialogObject;
import com.slack.api.model.block.composition.OptionObject;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.view.View;
import com.slack.api.model.view.ViewState;
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
    private Enterprise enterprise;
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
    private ViewState state; // for actions in a message
    private AppUnfurl appUnfurl;
    private List<Action> actions;
    private boolean isEnterpriseInstall;

    @Data
    public static class Enterprise {
        private String id;
        private String name;
    }

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
        private String viewId;
        private String text;
        @SerializedName("is_ephemeral")
        private boolean ephemeral;
        @SerializedName("is_app_unfurl")
        private boolean appUnfurl;
        private String appUnfurlUrl;
        private String threadTs;
    }

    @Data
    public static class Channel {
        private String id;
        private String name;
    }

    @Data
    public static class AppUnfurl {
        private Integer id;
        private List<LayoutBlock> blocks;
        private String fallback;
        private String botId;
        private String appUnfurlUrl;
        @SerializedName("is_app_unfurl")
        private boolean appUnfurl;
        private String appId;
    }

    @Data
    public static class Action {
        private String actionId;
        private String blockId;
        private Text text;
        private String value;
        private String type;
        private String style;
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

        // timepicker
        private String selectedTime;
        private String initialTime;

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
