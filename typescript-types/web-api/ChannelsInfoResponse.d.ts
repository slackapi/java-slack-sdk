export interface ChannelsInfoResponse {
    ok?:       boolean;
    channel?:  Channel;
    error?:    string;
    needed?:   string;
    provided?: string;
}

export interface Channel {
    id?:                   string;
    name?:                 string;
    is_channel?:           boolean;
    created?:              number;
    is_archived?:          boolean;
    is_general?:           boolean;
    unlinked?:             number;
    creator?:              string;
    name_normalized?:      string;
    is_shared?:            boolean;
    is_org_shared?:        boolean;
    is_member?:            boolean;
    is_private?:           boolean;
    is_mpim?:              boolean;
    last_read?:            string;
    latest?:               Latest;
    unread_count?:         number;
    unread_count_display?: number;
    members?:              string[];
    topic?:                Purpose;
    purpose?:              Purpose;
    previous_names?:       string[];
    is_read_only?:         boolean;
}

export interface Latest {
    user?:           string;
    type?:           string;
    subtype?:        string;
    ts?:             string;
    text?:           string;
    username?:       string;
    icons?:          Icons;
    bot_id?:         string;
    attachments?:    Attachment[];
    files?:          File[];
    upload?:         boolean;
    display_as_bot?: boolean;
}

export interface Attachment {
    service_name?: string;
    title?:        string;
    title_link?:   string;
    text?:         string;
    fallback?:     string;
    image_url?:    string;
    image_width?:  number;
    image_height?: number;
    ts?:           number;
    from_url?:     string;
    image_bytes?:  number;
    service_icon?: string;
    id?:           number;
    original_url?: string;
    footer?:       string;
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
}

export interface Purpose {
    value?:    string;
    creator?:  string;
    last_set?: number;
}
