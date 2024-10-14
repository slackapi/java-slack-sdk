package com.slack.api.bolt.service.builtin.oauth.view.default_impl;

import com.slack.api.bolt.service.builtin.oauth.view.OAuthInstallPageRenderer;
import org.apache.commons.text.StringEscapeUtils;

public class OAuthDefaultInstallPageRenderer implements OAuthInstallPageRenderer {

    // variables: __URL__
    public static final String PAGE_TEMPLATE = "<html>\n" +
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
            "<h2>Slack App Installation</h2>\n" +
            "<p><a href=\"__URL__\"><img alt=\"Add to Slack\" height=\"40\" width=\"139\" src=\"https://platform.slack-edge.com/img/add_to_slack.png\" srcset=\"https://platform.slack-edge.com/img/add_to_slack.png 1x, https://platform.slack-edge.com/img/add_to_slack@2x.png 2x\" /></a></p>\n" +
            "</body>\n" +
            "</html>";

    @Override
    public String render(String authorizeUrl) {
        String url = StringEscapeUtils.escapeHtml4(authorizeUrl);
        return PAGE_TEMPLATE.replaceAll("__URL__", url == null ? "" : url);
    }

}
