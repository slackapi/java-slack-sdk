export interface ConversationsListResponse {
    ok?:                boolean;
    channels?:          Channel[];
    response_metadata?: ResponseMetadata;
    error?:             string;
    needed?:            string;
    provided?:          string;
}

export interface Channel {
    id?:                    string;
    name?:                  string;
    is_channel?:            boolean;
    is_group?:              boolean;
    is_im?:                 boolean;
    created?:               number;
    is_archived?:           boolean;
    is_general?:            boolean;
    unlinked?:              number;
    name_normalized?:       string;
    is_shared?:             boolean;
    creator?:               string;
    is_ext_shared?:         boolean;
    is_org_shared?:         boolean;
    shared_team_ids?:       string[];
    pending_shared?:        any[];
    is_pending_ext_shared?: boolean;
    is_member?:             boolean;
    is_private?:            boolean;
    is_mpim?:               boolean;
    topic?:                 Purpose;
    purpose?:               Purpose;
    previous_names?:        any[];
    num_members?:           number;
    last_read?:             string;
    is_open?:               boolean;
    priority?:              number;
}

export interface Purpose {
    value?:    string;
    creator?:  string;
    last_set?: number;
}

export interface ResponseMetadata {
    next_cursor?: string;
}
