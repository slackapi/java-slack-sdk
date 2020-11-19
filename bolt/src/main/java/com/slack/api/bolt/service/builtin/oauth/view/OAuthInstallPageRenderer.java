package com.slack.api.bolt.service.builtin.oauth.view;

/**
 * Renders web pages for Slack app installers.
 */
public interface OAuthInstallPageRenderer {

    /**
     * Returns an HTML content for the page.
     *
     * @param authorizeUrl the Slack's authorize URL
     * @return html content
     */
    String render(String authorizeUrl);

}
