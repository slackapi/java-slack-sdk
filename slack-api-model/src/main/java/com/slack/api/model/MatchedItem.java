package com.slack.api.model;

import com.google.gson.annotations.SerializedName;
import com.slack.api.model.block.LayoutBlock;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class MatchedItem {

    private String iid;
    private String id;
    private String team;
    private String type;
    private Channel channel;
    private String user;
    private String username;
    private String ts;
    private String title;
    private String text;
    private List<File> files;
    private List<Attachment> attachments;
    private List<LayoutBlock> blocks;
    private String permalink;
    private String name;
    private String subject;
    private String preview;

    private String plainText;
    private String previewPlainText;

    private boolean hasMore;
    private boolean sentToSelf;
    private boolean noReactions;

    private String botId;

    private String userTeam;
    private String sourceTeam;

    private String fileId;
    private String externalId;
    private String externalUrl;

    private String timestamp;
    private String created;

    @SerializedName("is_intro")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private boolean intro;

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

    private String urlPrivate;
    private boolean urlPrivateDownload;

    private boolean permalinkPublic;

    private String editLink;
    private String previewHighlight;

    private Integer lines;
    private Integer linesMore;
    @SerializedName("preview_is_truncated")
    private boolean previewTruncated;
    private boolean hasRichPreview;

    private String mimetype;
    private String filetype;
    private String prettyType;
    @SerializedName("is_mpim")
    private boolean mpim;
    @SerializedName("is_external")
    private boolean external;
    @SerializedName("is_starred")
    private boolean starred;
    private String externalType;

    private boolean editable;
    private boolean nonOwnerEditable; // application/vnd.slack-docs
    private String editor; // application/vnd.slack-docs
    private String lastEditor; // application/vnd.slack-docs
    private Integer updated; // application/vnd.slack-docs

    private boolean displayAsBot;
    private Integer size;
    private String mode;
    private String comment;

    @SerializedName("previous_2")
    private OtherItem previous2;
    private OtherItem previous;
    private OtherItem next;
    @SerializedName("next_2")
    private OtherItem next2;

    private File.Shares shares;
    private List<String> channels;
    private List<String> groups;
    private List<String> ims;

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

    private String thumbTiny;

    private String thumbVideo;

    private String thumbPdf;
    @SerializedName("thumb_pdf_w")
    private String thumbPdfWidth;
    @SerializedName("thumb_pdf_h")
    private String thumbPdfHeight;

    private Integer imageExifRotation;

    @SerializedName("original_w")
    private String originalWidth;
    @SerializedName("original_h")
    private String originalHeight;

    private String score;
    private boolean topFile;

    private String deanimateGif;

    private String channelActionsTs;
    private Integer channelActionsCount;

    @Data
    public static class OtherItem {
        private String iid;
        private String type;
        private String user;
        private String username;
        private String ts;
        private String text;
        private List<Attachment> attachments;
        private List<LayoutBlock> blocks;
        private String permalink;
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

}
