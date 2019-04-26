export interface EventsLinkSharedPayload {
    token?:      string;
    team_id?:    string;
    api_app_id?: string;
    event?:      Event;
    type?:       string;
    event_id?:   string;
    event_time?: number;
}

export interface Event {
    type?:       string;
    channel?:    string;
    user?:       string;
    message_ts?: string;
    thread_ts?:  string;
}
