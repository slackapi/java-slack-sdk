export interface ChatScheduledMessagesListResponse {
    ok?:                 boolean;
    scheduled_messages?: ScheduledMessage[];
    response_metadata?:  ResponseMetadata;
}

export interface ResponseMetadata {
    next_cursor?: string;
}

export interface ScheduledMessage {
    id?:           string;
    channel_id?:   string;
    post_at?:      number;
    date_created?: number;
}
