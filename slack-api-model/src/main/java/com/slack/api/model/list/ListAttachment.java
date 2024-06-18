package com.slack.api.model.list;

import com.google.gson.annotations.SerializedName;
import com.slack.api.model.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListAttachment {
    private String id;
    private Integer created;
    private Integer timestamp;
    private String name;
    private String title;
    private String mimetype;
    private String filetype;
    private String externalType;
    private String prettyType;
    private String user;
    private String userTeam;
    private boolean editable;
    private Integer size;
    private String mode;
    @SerializedName("is_external")
    private boolean external;
    @SerializedName("is_public")
    private boolean isPublic;
    private boolean publicUrlShared;
    private boolean displayAsBot;
    private String username;
    private ListMetadata listMetadata;
    private ListLimits listLimits;
    private String urlPrivate;
    private String urlPrivateDownload;
    private String permalink;
    private String permalinkPublic;
    private String lastEditor;
    private Integer updated;
    private Integer commentsCount;
    private File.Shares shares;
    private List<String> channels;
    private List<String> groups;
    private List<String> ims;
    private boolean hasMoreShares;
    private Integer privateChannelsWithFileAccessCount;
    private Integer privateFileWithAccessCount;
    private List<File.UserWithFileAccess> dmMpdmUsersWithFileAccess;
    private boolean hasRichPreview;
    private String fileAccess;
}
