package com.github.seratch.jslack.lightning.request;

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
