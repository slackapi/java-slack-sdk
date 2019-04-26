export interface SearchFilesResponse {
    ok?:       boolean;
    query?:    string;
    files?:    Files;
    error?:    string;
    needed?:   string;
    provided?: string;
}

export interface Files {
    total?:      number;
    pagination?: Pagination;
    paging?:     Paging;
    matches?:    Match[];
}

export interface Match {
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
    groups?:               any[];
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
    public?:  Public;
    private?: Private;
}

export interface Private {
    DG79B5UNR?: Dg79B5Unr[];
}

export interface Dg79B5Unr {
    reply_users?:       any[];
    reply_users_count?: number;
    reply_count?:       number;
    ts?:                string;
}

export interface Public {
    C03E94MKS?: C03E94Mk[];
}

export interface C03E94Mk {
    reply_users?:       any[];
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
