export interface UsersConversationsResponse {
    ok?:                boolean;
    channels?:          any[];
    response_metadata?: ResponseMetadata;
}

export interface ResponseMetadata {
    next_cursor?: string;
}
