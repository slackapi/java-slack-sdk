export interface ConversationsHistoryResponse {
    ok?:                boolean;
    messages?:          Message[];
    has_more?:          boolean;
    pin_count?:         number;
    response_metadata?: ResponseMetadata;
}

export interface Message {
    type?:              string;
    subtype?:           string;
    text?:              string;
    user?:              string;
    ts?:                string;
    edited?:            Edited;
    bot_id?:            string;
    thread_ts?:         string;
    root?:              Root;
    username?:          string;
    reply_count?:       number;
    reply_users_count?: number;
    latest_reply?:      string;
    reply_users?:       string[];
    replies?:           Edited[];
    subscribed?:        boolean;
    last_read?:         string;
    blocks?:            Block[];
    icons?:             Icons;
    attachments?:       Attachment[];
    files?:             File[];
    upload?:            boolean;
    display_as_bot?:    boolean;
    reactions?:         Reaction[];
    topic?:             string;
    purpose?:           string;
    client_msg_id?:     string;
    inviter?:           string;
}

export interface Attachment {
    service_name?:          string;
    title?:                 string;
    title_link?:            string;
    text?:                  string;
    fallback?:              string;
    image_url?:             string;
    image_width?:           number;
    image_height?:          number;
    ts?:                    number | string;
    from_url?:              string;
    image_bytes?:           number;
    service_icon?:          string;
    id?:                    number;
    original_url?:          string;
    msg_subtype?:           string;
    author_subname?:        string;
    channel_id?:            string;
    channel_name?:          string;
    is_msg_unfurl?:         boolean;
    is_thread_root_unfurl?: boolean;
    author_icon?:           string;
    author_link?:           string;
    mrkdwn_in?:             string[];
    footer?:                string;
    author_name?:           string;
    color?:                 string;
    fields?:                Field[];
}

export interface Field {
    title?: string;
    value?: string;
    short?: boolean;
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
    text?:      ElementText;
    value?:     string;
}

export interface ElementText {
    type?:  string;
    text?:  string;
    emoji?: boolean;
}

export interface BlockText {
    type?:     string;
    text?:     string;
    verbatim?: boolean;
}

export interface Edited {
    user?: string;
    ts?:   string;
}

export interface File {
    id?:                   string;
    created?:              number;
    timestamp?:            number;
    name?:                 string;
    title?:                string;
    mimetype?:             string;
    filetype?:             string;
    pretty_type?:          string;
    user?:                 string;
    editable?:             boolean;
    size?:                 number;
    mode?:                 string;
    is_external?:          boolean;
    external_type?:        string;
    is_public?:            boolean;
    public_url_shared?:    boolean;
    display_as_bot?:       boolean;
    username?:             string;
    url_private?:          string;
    url_private_download?: string;
    permalink?:            string;
    permalink_public?:     string;
    edit_link?:            string;
    preview?:              string;
    preview_highlight?:    string;
    lines?:                number;
    lines_more?:           number;
    preview_is_truncated?: boolean;
    is_starred?:           boolean;
    has_rich_preview?:     boolean;
}

export interface Icons {
    image_48?: string;
    emoji?:    string;
    image_64?: string;
}

export interface Reaction {
    name?:  string;
    users?: string[];
    count?: number;
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
    replies?:           Edited[];
    subscribed?:        boolean;
    last_read?:         string;
}

export interface ResponseMetadata {
    next_cursor?: string;
}
