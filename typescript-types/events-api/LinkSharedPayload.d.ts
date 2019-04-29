export interface LinkSharedPayload {
    token?:        string;
    team_id?:      string;
    api_app_id?:   string;
    event?:        Event;
    type?:         string;
    authed_users?: string[];
    event_id?:     string;
    event_time?:   number;
}

export interface Event {
    type?:       string;
    channel?:    string;
    user?:       string;
    message_ts?: string;
    thread_ts?:  string;
    links?:      Link[];
}

export interface Link {
    domain?: string;
    url?:    string;
}
