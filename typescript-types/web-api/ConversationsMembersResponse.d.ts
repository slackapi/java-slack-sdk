export interface ConversationsMembersResponse {
    ok?:                boolean;
    members?:           string[];
    response_metadata?: ResponseMetadata;
}

export interface ResponseMetadata {
    next_cursor?: string;
}
