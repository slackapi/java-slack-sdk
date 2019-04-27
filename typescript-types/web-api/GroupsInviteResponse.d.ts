export interface GroupsInviteResponse {
    ok?:       boolean;
    error?:    string;
    group?:    Group;
    needed?:   string;
    provided?: string;
}

export interface Group {
    id?:                   string;
    name?:                 string;
    is_group?:             boolean;
    created?:              number;
    creator?:              string;
    is_archived?:          boolean;
    name_normalized?:      string;
    is_mpim?:              boolean;
    is_open?:              boolean;
    last_read?:            string;
    latest?:               Latest;
    unread_count?:         number;
    unread_count_display?: number;
    members?:              string[];
    topic?:                Purpose;
    purpose?:              Purpose;
    priority?:             number;
}

export interface Latest {
    user?:    string;
    type?:    string;
    subtype?: string;
    ts?:      string;
    text?:    string;
    topic?:   string;
}

export interface Purpose {
    value?:    string;
    creator?:  string;
    last_set?: number;
}
