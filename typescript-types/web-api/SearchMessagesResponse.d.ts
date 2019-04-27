export interface SearchMessagesResponse {
    ok?:       boolean;
    query?:    string;
    messages?: Messages;
    error?:    string;
    needed?:   string;
    provided?: string;
}

export interface Messages {
    total?:       number;
    pagination?:  Pagination;
    paging?:      Paging;
    matches?:     Match[];
    refinements?: string[];
}

export interface Match {
    iid?:       string;
    team?:      string;
    channel?:   Channel;
    type?:      string;
    user?:      string;
    username?:  string;
    ts?:        string;
    text?:      string;
    permalink?: string;
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
