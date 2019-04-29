export interface PinRemovedEvent {
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
    ts?:            string;
    permalink?:     string;
}
