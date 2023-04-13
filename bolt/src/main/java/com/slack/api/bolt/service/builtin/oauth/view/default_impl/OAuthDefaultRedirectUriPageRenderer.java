package com.slack.api.bolt.service.builtin.oauth.view.default_impl;

import com.slack.api.bolt.model.Installer;
import com.slack.api.bolt.service.builtin.oauth.view.OAuthRedirectUriPageRenderer;
import org.apache.commons.text.StringEscapeUtils;

public class OAuthDefaultRedirectUriPageRenderer implements OAuthRedirectUriPageRenderer {

    // variables: __URL__, __BROWSER_URL__
    public static final String SUCCESS_PAGE_TEMPLATE = "<html>\n" +
            "<head>\n" +
            "<meta http-equiv=\"refresh\" content=\"0; URL=__URL__\">\n" +
            "<style>\n" +
            "body {\n" +
            "  padding: 10px 15px;\n" +
            "  font-family: verdana;\n" +
            "  text-align: center;\n" +
            "}\n" +
            "</style>\n" +
            "</head>\n" +
            "<body>\n" +
            "<h2>Thank you!</h2>\n" +
            "<p>Redirecting to the Slack App... click <a href=\"__URL__\">here</a>. If you use the browser version of Slack, click <a href=\"__BROWSER_URL__\" target=\"_blank\">this link</a> instead.</p>\n" +
            "</body>\n" +
            "</html>";

    // variables: __INSTALL_PATH__, __REASON__
    public static final String FAILURE_PAGE_TEMPLATE = "<html>\n" +
            "<head>\n" +
            "<style>\n" +
            "body {\n" +
            "  padding: 10px 15px;\n" +
            "  font-family: verdana;\n" +
            "  text-align: center;\n" +
            "}\n" +
            "</style>\n" +
            "</head>\n" +
            "<body>\n" +
            "<h2>Oops, Something Went Wrong!</h2>\n" +
            "<p>Please try again from <a href=\"__INSTALL_PATH__\">here</a> or contact the app owner (reason: __REASON__)</p>\n" +
            "</body>\n" +
            "</html>";

    @Override
    public String renderSuccessPage(Installer installer, String completionUrl) {
        String url = completionUrl;
        if (url == null || url.isEmpty()) {
            if (installer == null) {
                url = "slack://open";
            } else {
                if (installer.getIsEnterpriseInstall() != null
                        && installer.getIsEnterpriseInstall()
                        && installer.getEnterpriseUrl() != null
                        && installer.getAppId() != null
                ) {
                    // org-level installation
                    String rootUrl = installer.getEnterpriseUrl(); // https://{org domain}.enterprise.slack.com/
                    url = rootUrl + "manage/organization/apps/profile/" + installer.getAppId() + "/workspaces/add";
                } else if (installer.getTeamId() == null || installer.getAppId() == null) {
                    url = "slack://open";
                } else {
                    url = "slack://app?team=" + installer.getTeamId() + "&id=" + installer.getAppId();
                }
            }
        }
        String browserUrl = installer == null || installer.getTeamId() == null
                ? "https://slack.com/"
                : "https://app.slack.com/client/" + installer.getTeamId();

        url = StringEscapeUtils.escapeHtml4(url);
        browserUrl = StringEscapeUtils.escapeHtml4(browserUrl);
        return SUCCESS_PAGE_TEMPLATE
                .replaceAll("__URL__", url == null ? "" : url)
                .replaceAll("__BROWSER_URL__", browserUrl);
    }

    @Override
    public String renderFailurePage(String installPath, String reason) {
        installPath = StringEscapeUtils.escapeHtml4(installPath);
        reason = StringEscapeUtils.escapeHtml4(reason);
        return FAILURE_PAGE_TEMPLATE
                .replaceAll("__INSTALL_PATH__", installPath == null ? "" : installPath)
                .replaceAll("__REASON__", reason == null ? "unknown_error" : reason);
    }
}
