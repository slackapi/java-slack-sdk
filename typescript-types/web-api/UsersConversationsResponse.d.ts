export interface UsersConversationsResponse {
    ok?:                boolean;
    channels?:          string[];
    response_metadata?: ResponseMetadata;
    error?:             string;
    needed?:            string;
    provided?:          string;
}

export interface ResponseMetadata {
    next_cursor?: string;
}
