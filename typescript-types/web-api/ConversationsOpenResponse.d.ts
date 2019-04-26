export interface ConversationsOpenResponse {
    ok?:           boolean;
    no_op?:        boolean;
    already_open?: boolean;
    channel?:      Channel;
    error?:        string;
    needed?:       string;
    provided?:     string;
}

export interface Channel {
    id?:                   string;
    created?:              number;
    is_im?:                boolean;
    is_org_shared?:        boolean;
    user?:                 string;
    last_read?:            string;
    latest?:               Latest;
    unread_count?:         number;
    unread_count_display?: number;
    is_open?:              boolean;
    priority?:             number;
}

export interface Latest {
    type?:      string;
    subtype?:   string;
    text?:      string;
    ts?:        string;
    username?:  string;
    bot_id?:    string;
    thread_ts?: string;
}
