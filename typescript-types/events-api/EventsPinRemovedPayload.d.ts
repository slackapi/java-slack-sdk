export interface EventsPinRemovedPayload {
    token?:        string;
    team_id?:      string;
    api_app_id?:   string;
    event?:        Event;
    type?:         string;
    authed_users?: string[];
    event_id?:     string;
    event_time?:   number;
}

export interface Event {
    type?:       string;
    user?:       string;
    channel_id?: string;
    item?:       Item;
    has_pins?:   boolean;
    event_ts?:   string;
}

export interface Item {
    type?:       string;
    channel?:    string;
    created_by?: string;
    created?:    number;
    message?:    Message;
    file?:       File;
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
    mode?:                 string;
    editable?:             boolean;
    is_external?:          boolean;
    external_type?:        string;
    username?:             string;
    size?:                 number;
    url_private?:          string;
    url_private_download?: string;
    thumb_64?:             string;
    thumb_64_gif?:         string;
    thumb_64_w?:           string;
    thumb_64_h?:           string;
    thumb_80?:             string;
    thumb_80_gif?:         string;
    thumb_80_w?:           string;
    thumb_80_h?:           string;
    thumb_160?:            string;
    thumb_160_gif?:        string;
    thumb_160_w?:          string;
    thumb_160_h?:          string;
    thumb_360?:            string;
    thumb_360_gif?:        string;
    thumb_360_w?:          string;
    thumb_360_h?:          string;
    thumb_480?:            string;
    thumb_480_gif?:        string;
    thumb_480_w?:          string;
    thumb_480_h?:          string;
    thumb_720?:            string;
    thumb_720_gif?:        string;
    thumb_720_w?:          string;
    thumb_720_h?:          string;
    thumb_800?:            string;
    thumb_800_gif?:        string;
    thumb_800_w?:          string;
    thumb_800_h?:          string;
    thumb_960?:            string;
    thumb_960_gif?:        string;
    thumb_960_w?:          string;
    thumb_960_h?:          string;
    thumb_1024?:           string;
    thumb_1024_gif?:       string;
    thumb_1024_w?:         string;
    thumb_1024_h?:         string;
    thumb_video?:          string;
    image_exif_rotation?:  number;
    original_w?:           string;
    original_h?:           string;
    deanimate_gif?:        string;
    pjpeg?:                string;
    permalink?:            string;
    permalink_public?:     string;
    edit_link?:            string;
    has_rich_preview?:     boolean;
    preview_is_truncated?: boolean;
    preview?:              string;
    preview_highlight?:    string;
    lines?:                number;
    lines_more?:           number;
    is_public?:            boolean;
    public_url_shared?:    boolean;
    display_as_bot?:       boolean;
    num_stars?:            number;
    is_starred?:           boolean;
    comments_count?:       number;
    shares?:               Shares;
}

export interface Shares {
}

export interface Message {
    client_msg_id?: string;
    type?:          string;
    user?:          string;
    text?:          string;
    blocks?:        Block[];
    attachments?:   Attachment[];
    ts?:            string;
    pinned_to?:     string[];
    permalink?:     string;
}

export interface Attachment {
    msg_subtype?:           string;
    fallback?:              string;
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
    id?:                    number;
    bot_id?:                string;
    is_msg_unfurl?:         boolean;
    is_reply_unfurl?:       boolean;
    is_thread_root_unfurl?: boolean;
    app_unfurl_url?:        string;
    is_app_unfurl?:         boolean;
    title?:                 string;
    title_link?:            string;
    text?:                  string;
    fields?:                Field[];
    image_url?:             string;
    image_width?:           number;
    image_height?:          number;
    image_bytes?:           number;
    thumb_url?:             string;
    thumb_width?:           number;
    thumb_height?:          number;
    footer?:                string;
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
    elements?:     Element[];
    block_id?:     string;
    fallback?:     string;
    image_url?:    string;
    image_width?:  number;
    image_height?: number;
    image_bytes?:  number;
    alt_text?:     string;
    title?:        Text;
    text?:         Text;
    fields?:       Text[];
    accessory?:    Accessory;
}

export interface Accessory {
    type?:         string;
    fallback?:     string;
    image_url?:    string;
    image_width?:  number;
    image_height?: number;
    image_bytes?:  number;
    alt_text?:     string;
}

export interface Element {
    type?:                 string;
    fallback?:             string;
    text?:                 Text;
    action_id?:            string;
    url?:                  string;
    value?:                string;
    style?:                string;
    confirm?:              Confirm;
    placeholder?:          Text;
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
    title?:   Text;
    text?:    Text;
    confirm?: Text;
    deny?:    Text;
}

export interface Text {
    type?:     Type;
    text?:     string;
    emoji?:    boolean;
    verbatim?: boolean;
}

export enum Type {
    Mrkdwn = "mrkdwn",
    PlainText = "plain_text",
}

export interface InitialOption {
    text?:  Text;
    value?: string;
}
