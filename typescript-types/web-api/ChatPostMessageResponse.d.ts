export interface ChatPostMessageResponse {
    ok?:       boolean;
    channel?:  string;
    ts?:       string;
    message?:  Message;
    error?:    string;
    needed?:   string;
    provided?: string;
}

export interface Message {
    type?:        string;
    subtype?:     string;
    text?:        string;
    ts?:          string;
    username?:    string;
    bot_id?:      string;
    thread_ts?:   string;
    root?:        Root;
    blocks?:      Block[];
    attachments?: Attachment[];
    user?:        string;
}

export interface Attachment {
    text?:     string;
    id?:       number;
    fallback?: string;
    footer?:   string;
}

export interface Block {
    type?:      string;
    block_id?:  string;
    text?:      BlockText;
    accessory?: Accessory;
    elements?:  Element[];
}

export interface Accessory {
    fallback?:     string;
    image_url?:    string;
    image_width?:  number;
    image_height?: number;
    image_bytes?:  number;
    type?:         string;
    alt_text?:     string;
}

export interface Element {
    type?:      string;
    action_id?: string;
    text?:      TextText | string;
    value?:     string;
    emoji?:     boolean;
    verbatim?:  boolean;
}

export interface TextText {
    type?:  string;
    text?:  string;
    emoji?: boolean;
}

export interface BlockText {
    type?:     string;
    text?:     string;
    verbatim?: boolean;
}

export interface Root {
    type?:              string;
    subtype?:           string;
    text?:              string;
    ts?:                string;
    username?:          string;
    bot_id?:            string;
    thread_ts?:         string;
    reply_count?:       number;
    reply_users_count?: number;
    latest_reply?:      string;
    reply_users?:       string[];
    replies?:           Reply[];
    subscribed?:        boolean;
    last_read?:         string;
}

export interface Reply {
    user?: string;
    ts?:   string;
}
