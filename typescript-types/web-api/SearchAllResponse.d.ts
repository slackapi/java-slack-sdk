export interface SearchAllResponse {
    ok?:       boolean;
    query?:    string;
    messages?: Messages;
    files?:    Files;
    posts?:    Posts;
    error?:    string;
    needed?:   string;
    provided?: string;
}

export interface Files {
    total?:      number;
    pagination?: Pagination;
    paging?:     Paging;
    matches?:    FilesMatch[];
}

export interface FilesMatch {
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
    thumb_64?:             string;
    thumb_80?:             string;
    thumb_360?:            string;
    thumb_360_w?:          number;
    thumb_360_h?:          number;
    thumb_160?:            string;
    image_exif_rotation?:  number;
    original_w?:           number;
    original_h?:           number;
    permalink?:            string;
    permalink_public?:     string;
    is_starred?:           boolean;
    shares?:               Shares;
    channels?:             string[];
    groups?:               string[];
    ims?:                  string[];
    has_rich_preview?:     boolean;
    score?:                string;
    top_file?:             boolean;
    thumb_480?:            string;
    thumb_480_w?:          number;
    thumb_480_h?:          number;
    thumb_720?:            string;
    thumb_720_w?:          number;
    thumb_720_h?:          number;
    thumb_800?:            string;
    thumb_800_w?:          number;
    thumb_800_h?:          number;
    thumb_960?:            string;
    thumb_960_w?:          number;
    thumb_960_h?:          number;
    thumb_1024?:           string;
    thumb_1024_w?:         number;
    thumb_1024_h?:         number;
    external_id?:          string;
    external_url?:         string;
    edit_link?:            string;
    preview?:              string;
    preview_highlight?:    string;
    lines?:                number;
    lines_more?:           number;
    preview_is_truncated?: boolean;
}

export interface Shares {
    public?:  { [key: string]: Public[] };
    private?: { [key: string]: Private[] };
}

export interface Private {
    reply_users?:       string[];
    reply_users_count?: number;
    reply_count?:       number;
    ts?:                string;
}

export interface Public {
    reply_users?:       string[];
    reply_users_count?: number;
    reply_count?:       number;
    ts?:                string;
    channel_name?:      string;
    team_id?:           string;
}

export interface Pagination {
    total_count?: number;
    page?:        number;
    per_page?:    number;
    page_count?:  number;
    first?:       number;
    last?:        number;
}

export interface Paging {
    count?: number;
    total?: number;
    page?:  number;
    pages?: number;
}

export interface Messages {
    total?:       number;
    pagination?:  Pagination;
    paging?:      Paging;
    matches?:     MessagesMatch[];
    refinements?: string[];
}

export interface MessagesMatch {
    iid?:         string;
    team?:        string;
    channel?:     Channel;
    type?:        string;
    user?:        string;
    username?:    string;
    ts?:          string;
    text?:        string;
    permalink?:   string;
    attachments?: Attachment[];
    previous?:    Previous;
    previous_2?:  Previous;
    blocks?:      Block[];
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
    text?: Text;
}

export interface Text {
    type?:     string;
    text?:     string;
    emoji?:    boolean;
    verbatim?: boolean;
}

export interface InitialOption {
    text?:  Text;
    value?: string;
}

export interface Channel {
    id?:                    string;
    is_channel?:            boolean;
    is_group?:              boolean;
    is_im?:                 boolean;
    name?:                  string;
    is_shared?:             boolean;
    is_org_shared?:         boolean;
    is_ext_shared?:         boolean;
    is_private?:            boolean;
    is_mpim?:               boolean;
    pending_shared?:        string[];
    is_pending_ext_shared?: boolean;
    user?:                  string;
}

export interface Previous {
    type?:        string;
    user?:        string;
    username?:    string;
    ts?:          string;
    text?:        string;
    iid?:         string;
    permalink?:   string;
    blocks?:      Block[];
    attachments?: Attachment[];
}

export interface Posts {
    total?:   number;
    matches?: string[];
}
