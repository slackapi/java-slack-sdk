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
    blocks?:      Block[];
    thread_ts?:   string;
    root?:        Root;
    attachments?: Attachment[];
    user?:        string;
}

export interface Attachment {
    text?:                  string;
    footer?:                string;
    id?:                    number;
    fallback?:              string;
    msg_subtype?:           string;
    callback_id?:           string;
    color?:                 string;
    pretext?:               string;
    service_url?:           string;
    service_name?:          string;
    service_icon?:          string;
    author_name?:           string;
    author_link?:           string;
    author_icon?:           string;
    from_url?:              string;
    original_url?:          string;
    author_subname?:        string;
    channel_id?:            string;
    channel_name?:          string;
    bot_id?:                string;
    is_msg_unfurl?:         boolean;
    is_reply_unfurl?:       boolean;
    is_thread_root_unfurl?: boolean;
    app_unfurl_url?:        string;
    is_app_unfurl?:         boolean;
    title?:                 string;
    title_link?:            string;
    fields?:                Field[];
    image_url?:             string;
    image_width?:           number;
    image_height?:          number;
    image_bytes?:           number;
    thumb_url?:             string;
    thumb_width?:           number;
    thumb_height?:          number;
    footer_icon?:           string;
    ts?:                    string;
    mrkdwn_in?:             string[];
    actions?:               Action[];
}

export interface Action {
    id?:               string;
    name?:             string;
    text?:             string;
    style?:            string;
    type?:             string;
    value?:            string;
    data_source?:      string;
    min_query_length?: number;
    url?:              string;
}

export interface Field {
    title?: string;
    value?: string;
    short?: boolean;
}

export interface Block {
    type?:         string;
    block_id?:     string;
    text?:         TextElement;
    accessory?:    Accessory;
    elements?:     Element[];
    fallback?:     string;
    image_url?:    string;
    image_width?:  number;
    image_height?: number;
    image_bytes?:  number;
    alt_text?:     string;
    title?:        TextElement;
    fields?:       TextElement[];
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
    type?:                 string;
    text?:                 TextElement | string;
    emoji?:                boolean;
    action_id?:            string;
    value?:                string;
    verbatim?:             boolean;
    fallback?:             string;
    url?:                  string;
    style?:                string;
    confirm?:              Confirm;
    placeholder?:          TextElement;
    initial_channel?:      string;
    initial_conversation?: string;
    initial_date?:         string;
    initial_option?:       InitialOption;
    min_query_length?:     number;
    image_url?:            string;
    image_width?:          number;
    image_height?:         number;
    image_bytes?:          number;
    alt_text?:             string;
    initial_user?:         string;
}

export interface Confirm {
    text?: TextElement;
}

export interface TextElement {
    type?:     string;
    text?:     string;
    emoji?:    boolean;
    verbatim?: boolean;
}

export interface InitialOption {
    text?:  TextElement;
    value?: string;
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
