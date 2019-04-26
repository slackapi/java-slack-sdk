export interface ChannelsRepliesResponse {
    messages?: Message[];
    has_more?: boolean;
    ok?:       boolean;
}

export interface Message {
    user?:              string;
    type?:              string;
    subtype?:           string;
    ts?:                string;
    text?:              string;
    topic?:             string;
    username?:          string;
    bot_id?:            string;
    thread_ts?:         string;
    reply_count?:       number;
    reply_users_count?: number;
    latest_reply?:      string;
    reply_users?:       string[];
    replies?:           Reply[];
    subscribed?:        boolean;
}

export interface Reply {
    user?: string;
    ts?:   string;
}
