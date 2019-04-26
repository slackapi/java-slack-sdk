export interface GroupsRenameResponse {
    channel?: Channel;
    ok?:      boolean;
}

export interface Channel {
    id?:              string;
    name?:            string;
    is_group?:        boolean;
    created?:         number;
    creator?:         string;
    is_archived?:     boolean;
    name_normalized?: string;
    is_mpim?:         boolean;
    members?:         string[];
    topic?:           Purpose;
    purpose?:         Purpose;
}

export interface Purpose {
    value?:    string;
    creator?:  string;
    last_set?: number;
}
