package com.slack.api.bolt.service.builtin.oauth.view;

import com.slack.api.bolt.model.Installer;

/**
 * Renders web pages for Slack app installers.
 */
public interface OAuthRedirectUriPageRenderer {

    /**
     * Returns an HTML content for the success page.
     *
     * @param installer     installation information
     * @param completionUrl the static URL to navigate installers
     * @return html content
     */
    String renderSuccessPage(Installer installer, String completionUrl);

    /**
     * Returns an HTML content for the error page.
     *
     * @param installPath the default is /slack/install
     * @param reason      error reason
     * @return html content
     */
    String renderFailurePage(String installPath, String reason);

}
