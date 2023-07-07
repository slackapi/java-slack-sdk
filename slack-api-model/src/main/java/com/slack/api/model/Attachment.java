package com.slack.api.model;

import com.google.gson.annotations.SerializedName;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.PlainTextObject;
import lombok.*;

import java.util.List;

/**
 * https://api.slack.com/docs/message-attachments
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {

    private String msgSubtype; // "bot_message"

    /**
     * A plain-text summary of the attachment. This text will be used in clients that don't show formatted text
     * (e.g. IRC, mobile notifications) and should not contain any markup.
     */
    private String fallback;

    /**
     * This is optional value that specifies callback id when there are buttons.
     */
    private String callbackId;

    /**
     * Like traffic signals, color-coding messages can quickly communicate intent
     * and help separate them from the flow of other messages in the timeline.
     * <p>
     * An optional value that can either be one of good, warning, danger, or any hex color code (eg. #439FE0).
     * This value is used to color the border along the left side of the message attachment.
     */
    private String color;

    /**
     * This is optional text that appears above the message attachment block.
     */
    private String pretext;

    private String serviceUrl;

    private String serviceName;

    private String serviceIcon;

    // -----------------------------------------
    // The author parameters will display a small section at the top of a message attachment that can contain the following fields:

    private String authorId;

    /**
     * Small text used to display the author's name.
     */
    private String authorName;

    /**
     * A valid URL that will hyperlink the author_name text mentioned above. Will only work if author_name is present.
     */
    private String authorLink;

    /**
     * A valid URL that displays a small 16x16px image to the left of the author_name text. Will only work if author_name is present.
     */
    private String authorIcon;

    // -----------------------------------------
    // Attributes for reply_broadcast message (showing a posted message as an attachment)
    // https://api.slack.com/events/message/reply_broadcast

    // "from_url": "https://lost-generation-authors.slack.com/archives/general/p1482960137003543",
    private String fromUrl;

    private String originalUrl;

    // "author_subname": "confused",
    private String authorSubname;
    // "channel_id": "C061EG9SL",
    private String channelId;
    // "channel_name": "general",
    private String channelName;
    private String channelTeam;
    //"id": 1,
    private Integer id;

    private String appId;
    private String botId;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Boolean indent;

    public Boolean isIndent() {
        return this.indent;
    }

    public void setIndent(Boolean indent) {
        this.indent = indent;
    }

    // # already exists > "fallback": "[December 28th, 2016 1:22 PM] confused: what was there?",
    // # already exists > "ts": "1482960137.003543",
    // # already exists > "author_link": "https://lost-generation-authors.slack.com/team/confused",
    // # already exists > "author_icon": "https://...png",
    // # already exists > "mrkdwn_in": ["text"],
    // # already exists > "text": "island",
    // # already exists > "footer": "5 replies"

    /**
     * NOTE: The following Booleans (is_msg_unfurl, is_reply_unfurl, is_thread_root_unfurl,
     * is_app_unfurl) default to null intentionally to support block attachments.
     */

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @SerializedName("is_msg_unfurl")
    private Boolean msgUnfurl;

    public Boolean isMsgUnfurl() {
        return this.msgUnfurl;
    }

    public void setMsgUnfurl(Boolean msgUnfurl) {
        this.msgUnfurl = msgUnfurl;
    }

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @SerializedName("is_reply_unfurl")
    private Boolean replyUnfurl;

    public Boolean isReplyUnfurl() {
        return this.replyUnfurl;
    }

    public void setReplyUnfurl(Boolean replyUnfurl) {
        this.replyUnfurl = replyUnfurl;
    }

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @SerializedName("is_thread_root_unfurl")
    private Boolean threadRootUnfurl;

    public Boolean isThreadRootUnfurl() {
        return this.threadRootUnfurl;
    }

    public void setThreadRootUnfurl(Boolean threadRootUnfurl) {
        this.threadRootUnfurl = threadRootUnfurl;
    }

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @SerializedName("is_app_unfurl")
    private Boolean appUnfurl;

    public Boolean isAppUnfurl() {
        return this.appUnfurl;
    }

    public void setAppUnfurl(Boolean appUnfurl) {
        this.appUnfurl = appUnfurl;
    }

    private String appUnfurlUrl;

    /**
     * The title is displayed as larger, bold text near the top of a message attachment.
     * By passing a valid URL in the title_link parameter (optional), the title text will be hyperlinked.
     */
    private String title;

    /**
     * The title is displayed as larger, bold text near the top of a message attachment.
     * By passing a valid URL in the title_link parameter (optional), the title text will be hyperlinked.
     */
    private String titleLink;

    /**
     * This is the main text in a message attachment, and can contain standard message markup (see details below).
     * The content will automatically collapse if it contains 700+ characters or 5+ linebreaks,
     * and will display a "Show more..." link to expand the content.
     * <p>
     * https://api.slack.com/docs/message-attachments#message_formatting
     */
    private String text;

    /**
     * Fields are defined as an array, and hashes contained within it will be displayed in a table inside the message attachment.
     */
    private List<Field> fields;

    /**
     * A valid URL to an image file that will be displayed inside a message attachment.
     * We currently support the following formats: GIF, JPEG, PNG, and BMP.
     * <p>
     * Large images will be resized to a maximum width of 400px or a maximum height of 500px,
     * while still maintaining the original aspect ratio.
     */
    private String imageUrl;

    private Integer imageWidth;
    private Integer imageHeight;
    private Integer imageBytes;

    /**
     * A valid URL to an image file that will be displayed as a thumbnail on the right side of a message attachment.
     * We currently support the following formats: GIF, JPEG, PNG, and BMP.
     * <p>
     * The thumbnail's longest dimension will be scaled down to 75px while maintaining the aspect ratio of the image.
     * The filesize of the image must also be less than 500 KB.
     * <p>
     * For best results, please use images that are already 75px by 75px.
     */
    private String thumbUrl;

    private Integer thumbWidth;
    private Integer thumbHeight;

    private String videoUrl;
    private VideoHtml videoHtml;

    @Data
    public static class VideoHtml {
        private String html;
        private String source;
    }

    private Integer videoHtmlWidth;
    private Integer videoHtmlHeight;

    // Your message attachments may also contain a subtle footer,
    // which is especially useful when citing content in conjunction with author parameters.

    /**
     * Add some brief text to help contextualize and identify an attachment.
     * Limited to 300 characters, and may be truncated further when displayed to users in environments with limited screen real estate.
     */
    private String footer;

    /**
     * To render a small icon beside your footer text, provide a publicly accessible URL string in the footer_icon field.
     * You must also provide a footer for the field to be recognized.
     * <p>
     * We'll render what you provide at 16px by 16px. It's best to use an image that is similarly sized.
     */
    private String footerIcon;

    /**
     * ts (timestamp)
     * Does your attachment relate to something happening at a specific time?
     * <p>
     * By providing the ts field with an integer value in "epoch time",
     * the attachment will display an additional timestamp value as part of the attachment's footer.
     * <p>
     * Use ts when referencing articles or happenings. Your message will have its own timestamp when published.
     */
    private String ts;

    /**
     * By default,
     * <a href="https://api.slack.com/docs/message-formatting#message_formatting">message text
     * in attachments</a> are not formatted. To enable formatting on attachment fields, add the
     * name of the field (as defined in the API) in this list.
     */
    private List<String> mrkdwnIn;

    /**
     * Actions are defined as an array, and hashes contained within it will be displayed in as buttons in the message attachment.
     */
    private List<Action> actions;

    private List<LayoutBlock> blocks;

    // This property can exist in some scenarios
    // see https://github.com/slackapi/java-slack-sdk/issues/1179
    private List<LayoutBlock> messageBlocks;

    private Preview preview;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Preview {
        private String type; // "app"
        private boolean canRemove;
        private PlainTextObject title;
        private PlainTextObject subtitle;
        private String iconUrl;
    }

    // --------------------------
    // Files

    // files in a message included in this attachment
    private List<File> files;

    // single file data

    private String filename;
    private Integer size;
    private String mimetype;
    private String url;
    private AttachmentMetadata metadata;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AttachmentMetadata {

        @SerializedName("thumb_64")
        private Boolean thumb64;
        @SerializedName("thumb_80")
        private Boolean thumb80;
        @SerializedName("thumb_160")
        private Boolean thumb160;

        @SerializedName("original_w")
        private Integer originalWidth;
        @SerializedName("original_h")
        private Integer originalHeight;

        @SerializedName("thumb_360_w")
        private Integer thumb360Width;
        @SerializedName("thumb_360_h")
        private Integer thumb360Height;

        private String format;
        private String extension;
        private Integer rotation;
        private String thumbTiny;
    }

}
