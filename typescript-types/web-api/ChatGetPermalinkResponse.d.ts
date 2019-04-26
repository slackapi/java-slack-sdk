export interface ChatGetPermalinkResponse {
    ok?:        boolean;
    permalink?: string;
    channel?:   string;
    error?:     string;
    needed?:    string;
    provided?:  string;
}
