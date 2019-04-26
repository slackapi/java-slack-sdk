export interface ImListResponse {
    ok?:                boolean;
    ims?:               Im[];
    response_metadata?: ResponseMetadata;
}

export interface Im {
    id?:              string;
    created?:         number;
    is_im?:           boolean;
    is_org_shared?:   boolean;
    user?:            string;
    is_user_deleted?: boolean;
    priority?:        number;
}

export interface ResponseMetadata {
    next_cursor?: string;
}
