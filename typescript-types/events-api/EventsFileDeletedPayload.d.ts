export interface EventsFileDeletedPayload {
    token?:      string;
    team_id?:    string;
    api_app_id?: string;
    event?:      Event;
    type?:       string;
    event_id?:   string;
    event_time?: number;
}

export interface Event {
    type?:     string;
    file_id?:  string;
    event_ts?: string;
}
