export interface GroupsHistoryResponse {
    ok?:       boolean;
    messages?: Message[];
    has_more?: boolean;
    error?:    string;
    needed?:   string;
    provided?: string;
}

export interface Message {
    user?:    string;
    type?:    string;
    subtype?: string;
    ts?:      string;
    text?:    string;
}
