package com.github.seratch.jslack.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * https://api.slack.com/types/file
 */
@Data
@Builder
public class File {

    private String id;
    private Integer created;
    private Integer timestamp;
    private String name;
    private String title;
    private String mimetype;
    private String filetype;
    private String prettyType;
    private String user;
    private String mode;
    private boolean editable;
    @SerializedName("is_external")
    private boolean external;
    private String externalType;
    private String username;
    private Integer size;
    private String urlPrivate;
    private String urlPrivateDownload;

    @SerializedName("thumb_64")
    private String thumb64;
    @SerializedName("thumb_80")
    private String thumb80;

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
    @SerializedName("thumb_480_w")
    private String thumb480Width;
    @SerializedName("thumb_480_h")
    private String thumb480Height;

    @SerializedName("thumb_160")
    private String thumb160;

    private Integer imageExifRotation;

    @SerializedName("original_w")
    private String originalWidth;
    @SerializedName("original_h")
    private String originalHeight;

    private String deanimateGif;
    private String pjpeg;

    private String permalink;
    private String permalinkPublic;

    private String editLink;

    private boolean hasRichPreview;
    @SerializedName("preview_is_truncated")
    private boolean previewTruncated;
    private String preview;
    private String previewHighlight;

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
    private FileComment initialComment;
    private Integer numStars;

    @SerializedName("is_starred")
    private boolean starred;

    private List<String> pinnedTo;
    private List<Reaction> reactions;
    private Integer commentsCount;

    private Shares shares;

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
        private List<String> replyUsers;
        private Integer replyUsersCount;
        private Integer replyCount;
        private String ts;
        private String threadTs;
        private String latestReply;
        private String channelName;
        private String teamId;
    }

}