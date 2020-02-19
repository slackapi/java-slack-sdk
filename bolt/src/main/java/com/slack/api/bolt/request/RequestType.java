package com.slack.api.bolt.request;

/**
 * All the possible request types for incoming requests.
 */
public enum RequestType {
    OAuthStart,
    OAuthCallback,
    Command,
    SSLCheck,
    OutgoingWebhooks,
    Event,
    UrlVerification,
    AttachmentAction,
    BlockAction,
    BlockSuggestion,
    MessageAction,
    DialogSubmission,
    DialogCancellation,
    DialogSuggestion,
    ViewSubmission,
    ViewClosed
}
