package com.slack.api.model;

import com.google.gson.annotations.SerializedName;
import com.slack.api.model.block.LayoutBlock;
import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * https://api.slack.com/types/file
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class File {

    private String id;
    private Integer created;
    private Integer timestamp;
    private String name;
    private String title;
    private String subject;
    private String mimetype;
    private String filetype;
    private String prettyType;
    private String user;
    private String userTeam;
    private String sourceTeam;
    private String mode;
    private boolean editable;
    private boolean nonOwnerEditable; // application/vnd.slack-docs
    private String editor; // application/vnd.slack-docs
    private String lastEditor; // application/vnd.slack-docs
    private Integer updated; // application/vnd.slack-docs
    private String fileAccess;
    private String altTxt;

    private String subtype; // "slack_video" = clip
    private Transcription transcription; // when subtype is "slack_video"
    private String mp4; // file URL when subtype is "slack_video"
    private String mp4Low; // file URL when subtype is "slack_video"
    private String vtt; // file URL when subtype is "slack_video"
    private String hls; // file URL when subtype is "slack_video"
    private String hlsEmbed; // data:application/vnd.apple.mpegurl; ... when subtype is "slack_video"
    private Integer durationMs; // file URL when subtype is "slack_video"
    private Integer thumbVideoW; // file URL when subtype is "slack_video"
    private Integer thumbVideoH; // file URL when subtype is "slack_video"

    @Data
    public static class Transcription {
        private String status;
        private String locale;
    }

    private Integer originalAttachmentCount;

    @SerializedName("is_external")
    private boolean external;
    private String externalType;
    private String externalId;
    private String externalUrl;

    private String username;
    private Integer size;
    private String urlPrivate;
    private String urlPrivateDownload;
    private String urlStaticPreview;

    private String appId;
    private String appName;

    @SerializedName("thumb_64")
    private String thumb64;
    @SerializedName("thumb_64_gif")
    private String thumb64Gif;
    @SerializedName("thumb_64_w")
    private String thumb64Width;
    @SerializedName("thumb_64_h")
    private String thumb64Height;

    @SerializedName("thumb_80")
    private String thumb80;
    @SerializedName("thumb_80_gif")
    private String thumb80Gif;
    @SerializedName("thumb_80_w")
    private String thumb80Width;
    @SerializedName("thumb_80_h")
    private String thumb80Height;

    @SerializedName("thumb_160")
    private String thumb160;
    @SerializedName("thumb_160_gif")
    private String thumb160Gif;
    @SerializedName("thumb_160_w")
    private String thumb160Width;
    @SerializedName("thumb_160_h")
    private String thumb160Height;

    @SerializedName("thumb_360")
    private String thumb360;
    @SerializedName("thumb_360_gif")
    private String thumb360Gif;
    @SerializedName("thumb_360_w")
    private String thumb360Width;
    @SerializedName("thumb_360_h")
    private String thumb360Height;

    @SerializedName("thumb_480")
    private String thumb480;
    @SerializedName("thumb_480_gif")
    private String thumb480Gif;
    @SerializedName("thumb_480_w")
    private String thumb480Width;
    @SerializedName("thumb_480_h")
    private String thumb480Height;

    @SerializedName("thumb_720")
    private String thumb720;
    @SerializedName("thumb_720_gif")
    private String thumb720Gif;
    @SerializedName("thumb_720_w")
    private String thumb720Width;
    @SerializedName("thumb_720_h")
    private String thumb720Height;

    @SerializedName("thumb_800")
    private String thumb800;
    @SerializedName("thumb_800_gif")
    private String thumb800Gif;
    @SerializedName("thumb_800_w")
    private String thumb800Width;
    @SerializedName("thumb_800_h")
    private String thumb800Height;

    @SerializedName("thumb_960")
    private String thumb960;
    @SerializedName("thumb_960_gif")
    private String thumb960Gif;
    @SerializedName("thumb_960_w")
    private String thumb960Width;
    @SerializedName("thumb_960_h")
    private String thumb960Height;

    @SerializedName("thumb_1024")
    private String thumb1024;
    @SerializedName("thumb_1024_gif")
    private String thumb1024Gif;
    @SerializedName("thumb_1024_w")
    private String thumb1024Width;
    @SerializedName("thumb_1024_h")
    private String thumb1024Height;

    private String thumbVideo;

    @SerializedName("thumb_gif")
    private String thumbGif;
    @SerializedName("thumb_pdf")
    private String thumbPdf;
    @SerializedName("thumb_pdf_w")
    private String thumbPdfWidth;
    @SerializedName("thumb_pdf_h")
    private String thumbPdfHeight;

    private String thumbTiny;

    private String convertedPdf;

    private Integer imageExifRotation;

    @SerializedName("original_w")
    private String originalWidth;
    @SerializedName("original_h")
    private String originalHeight;

    private String deanimate;
    private String deanimateGif;
    private String pjpeg;

    private String permalink;
    private String permalinkPublic;

    private String editLink;

    private boolean hasRichPreview;
    private String mediaDisplayType;
    @SerializedName("preview_is_truncated")
    private boolean previewTruncated;
    private String preview;
    private String previewHighlight;

    private String plainText;
    private String previewPlainText;

    private boolean hasMore;

    private boolean sentToSelf;

    private Integer lines;
    private Integer linesMore;

    @SerializedName("is_public")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private boolean _public;

    public boolean isPublic() {
        return _public;
    }

    public void setPublic(boolean isPublic) {
        this._public = isPublic;
    }

    private boolean publicUrlShared;
    private boolean displayAsBot;

    private List<String> channels;
    private List<String> groups;
    private List<String> ims;

    private Shares shares;
    private Boolean hasMoreShares;

    @Data
    public static class Shares {

        /**
         * The key of the Map: channel ID
         */
        @SerializedName("public")
        private Map<String, List<ShareDetail>> publicChannels;

        /**
         * The key of the Map: channel ID
         */
        @SerializedName("private")
        private Map<String, List<ShareDetail>> privateChannels;

    }

    @Data
    public static class ShareDetail {
        private String shareUserId;
        private List<String> replyUsers;
        private Integer replyUsersCount;
        private Integer replyCount;
        private String ts;
        private String threadTs;
        private String latestReply;
        private String channelName;
        private String teamId;
        private String access; // "read"
    }

    private List<Address> to;
    private List<Address> from;
    private List<Address> cc;

    @Data
    public static class Address {
        private String address;
        private String name;
        private String original;
    }

    private Map<String, PinnedInfo> pinnedInfo; // C00000000 -> {}

    @Data
    public static class PinnedInfo {
        private String pinnedBy; // U00000000
        private Integer pinnedTs;
    }

    private String channelActionsTs;
    private Integer channelActionsCount;

    private Headers headers;
    private String simplifiedHtml;

    // for Email App's attachments
    @Data
    public static class Headers {
        private String date;
        private String inReplyTo;
        private String replyTo;
        private String messageId;
    }

    private MediaProgress mediaProgress;

    @Data
    public static class MediaProgress {
        private Integer offsetMs;
        private Integer maxOffsetMs;
        private Integer durationMs;
    }

    private Saved saved;

    @Data
    public static class Saved {
        private Boolean isArchived;
        private Integer dateCompleted;
        private Integer dateDue;
        private String state;
    }

    private String quipThreadId;
    @SerializedName("is_channel_space")
    private boolean channelSpace;
    private String linkedChannelId;
    private String access;
    private List<String> teamsSharedWith;
    private Long lastRead;
    private List<LayoutBlock> titleBlocks;
    private Integer privateChannelsWithFileAccessCount;
    private List<UserWithFileAccess> dmMpdmUsersWithFileAccess;
    private String orgOrWorkspaceAccess;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserWithFileAccess {
        private String userId;
        private String access;
    }

    private Integer updateNotification;
    private String canvasTemplateMode; // "published"
    private Integer templateConversionTs;
    private String templateName;
    private String templateTitle;
    private String templateDescription;
    private String templateIcon;

    // ---------------------------------------
    // file comments
    // https://api.slack.com/changelog/2018-05-file-threads-soon-tread

    @Deprecated
    private String botId;

    @Deprecated
    private FileComment initialComment;
    @Deprecated
    private Integer numStars;
    @Deprecated
    @SerializedName("is_starred")
    private boolean starred;
    @Deprecated
    private List<String> pinnedTo;
    @Deprecated
    private List<Reaction> reactions;
    @Deprecated
    private Integer commentsCount;
    @Deprecated
    private List<Attachment> attachments;
    @Deprecated
    private List<LayoutBlock> blocks;
    // ---------------------------------------

}