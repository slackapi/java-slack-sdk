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