export interface GroupsHistoryResponse {
    ok?:       boolean;
    messages?: Message[];
    has_more?: boolean;
}

export interface Message {
    user?:    string;
    type?:    string;
    subtype?: string;
    ts?:      string;
    text?:    string;
}
