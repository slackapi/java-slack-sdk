export interface ChatDeleteResponse {
    ok?:       boolean;
    channel?:  string;
    ts?:       string;
    error?:    string;
    needed?:   string;
    provided?: string;
}
