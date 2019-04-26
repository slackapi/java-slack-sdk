export interface ChannelsRenameResponse {
    channel?: Channel;
    ok?:      boolean;
}

export interface Channel {
    id?:              string;
    name?:            string;
    is_channel?:      boolean;
    created?:         number;
    is_archived?:     boolean;
    is_general?:      boolean;
    unlinked?:        number;
    creator?:         string;
    name_normalized?: string;
    is_shared?:       boolean;
    is_org_shared?:   boolean;
    is_member?:       boolean;
    is_private?:      boolean;
    is_mpim?:         boolean;
    members?:         any[];
    topic?:           Purpose;
    purpose?:         Purpose;
    previous_names?:  string[];
}

export interface Purpose {
    value?:    string;
    creator?:  string;
    last_set?: number;
}
