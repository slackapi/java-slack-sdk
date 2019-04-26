export interface ChatScheduleMessageResponse {
    ok?:                   boolean;
    error?:                string;
    scheduled_message_id?: string;
    channel?:              string;
    post_at?:              number;
    message?:              Message;
}

export interface Message {
    bot_id?:      string;
    type?:        string;
    text?:        string;
    user?:        string;
    attachments?: Attachment[];
    blocks?:      Block[];
}

export interface Attachment {
    text?:     string;
    id?:       number;
    fallback?: string;
}

export interface Block {
    type?:         string;
    block_id?:     string;
    image_url?:    string;
    alt_text?:     string;
    title?:        Title;
    fallback?:     string;
    image_width?:  number;
    image_height?: number;
    image_bytes?:  number;
}

export interface Title {
    type?:  string;
    text?:  string;
    emoji?: boolean;
}
