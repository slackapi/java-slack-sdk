export interface EventsImHistoryChangedPayload {
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
    latest?:   string;
    ts?:       string;
    event_ts?: string;
}
