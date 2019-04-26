export interface FilesListResponse {
    ok?:       boolean;
    files?:    File[];
    paging?:   Paging;
    error?:    string;
    needed?:   string;
    provided?: string;
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
    channels?:             string[];
    groups?:               any[];
    ims?:                  any[];
    comments_count?:       number;
    thumb_video?:          string;
}

export interface Paging {
    count?: number;
    total?: number;
    page?:  number;
    pages?: number;
}
