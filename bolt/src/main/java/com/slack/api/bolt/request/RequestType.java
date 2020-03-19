package com.slack.api.bolt.request;

/**
 * All the possible request types for incoming requests.
 */
public enum RequestType {
    OAuthStart,
    OAuthCallback,
    Command,
    SSLCheck,
    Event,
    UrlVerification,
    AttachmentAction,
    BlockAction,
    BlockSuggestion,
    GlobalShortcut,
    MessageShortcut,
    DialogSubmission,
    DialogCancellation,
    DialogSuggestion,
    ViewSubmission,
    ViewClosed
}
