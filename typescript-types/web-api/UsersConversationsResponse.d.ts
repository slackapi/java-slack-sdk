export interface UsersConversationsResponse {
    ok?:                boolean;
    channels?:          any[];
    response_metadata?: ResponseMetadata;
    error?:             string;
    needed?:            string;
    provided?:          string;
}

export interface ResponseMetadata {
    next_cursor?: string;
}
