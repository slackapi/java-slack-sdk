package com.slack.api.model.work_objects;

import com.google.gson.annotations.SerializedName;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.util.annotation.Required;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
public class DefaultAction extends PrimaryActions {
    @Required Type type;
    String style;
    PlainTextObject text;

    @Getter
    @RequiredArgsConstructor
    public enum Type {
        @SerializedName("add-to-todo") ADD_TO_TODO("add-to-todo"),
        @SerializedName("open-in-app") OPEN_IN_APP("open-in-app"),
        @SerializedName("share-link") SHARE_LINK("share-link"),
        @SerializedName("copy-link") COPY_LINK("copy-link"),
        @SerializedName("add-to-list") ADD_TO_LIST("add-to-list"),
        @SerializedName("save-for-later") SAVE_FOR_LATER("save-for-later"),
        @SerializedName("remind-me") REMIND_ME("remind-me"),
        @SerializedName("add-to-folder") ADD_TO_FOLDER("add-to-folder");

        private final String value;
    }
}
