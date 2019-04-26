export interface ChatPostEphemeralResponse {
    ok?:         boolean;
    message_ts?: string;
    error?:      string;
    needed?:     string;
    provided?:   string;
}
