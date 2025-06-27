package com.slack.api.methods.response.openid.connect;

import com.google.gson.annotations.SerializedName;
import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * https://docs.slack.dev/reference/methods/openid.connect.userInfo
 */
@Data
public class OpenIDConnectUserInfoResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private String sub; // W1234567890
    @SerializedName("https://slack.com/user_id")
    private String userId; // W1234567890
    @SerializedName("https://slack.com/team_id")
    private String teamId; // T1234567890
    @SerializedName("https://slack.com/enterprise_id")
    private String enterpriseId; // E1234567890
    private String email; // foo@example.com
    private boolean emailVerified; // true/false
    private Integer dateEmailVerified; // 1626075692
    private String name; // Cal Henderson
    private String picture; // https://avatars.slack-edge.com/xxx_512.jpg
    private String givenName; // Cal
    private String familyName; // Henderson
    private String locale; // ja-JP
    @SerializedName("https://slack.com/team_name")
    private String teamName;
    @SerializedName("https://slack.com/team_domain")
    private String team_domain;
    @SerializedName("https://slack.com/enterprise_name")
    private String enterpriseName;
    @SerializedName("https://slack.com/enterprise_domain")
    private String enterpriseDomain;
    @SerializedName("https://slack.com/user_image_24")
    private String userImage24;
    @SerializedName("https://slack.com/user_image_32")
    private String userImage32;
    @SerializedName("https://slack.com/user_image_48")
    private String userImage48;
    @SerializedName("https://slack.com/user_image_72")
    private String userImage72;
    @SerializedName("https://slack.com/user_image_192")
    private String userImage192;
    @SerializedName("https://slack.com/user_image_512")
    private String userImage512;
    @SerializedName("https://slack.com/user_image_1024")
    private String userImage1024;
    @SerializedName("https://slack.com/team_image_34")
    private String teamImage34;
    @SerializedName("https://slack.com/team_image_44")
    private String teamImage44;
    @SerializedName("https://slack.com/team_image_68")
    private String teamImage68;
    @SerializedName("https://slack.com/team_image_88")
    private String teamImage88;
    @SerializedName("https://slack.com/team_image_102")
    private String teamImage102;
    @SerializedName("https://slack.com/team_image_132")
    private String teamImage132;
    @SerializedName("https://slack.com/team_image_230")
    private String teamImage230;
}
