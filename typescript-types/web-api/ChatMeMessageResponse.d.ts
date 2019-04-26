export interface ChatMeMessageResponse {
    channel?:  string;
    ts?:       string;
    ok?:       boolean;
    error?:    string;
    needed?:   string;
    provided?: string;
}
