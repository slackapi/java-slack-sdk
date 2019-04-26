export interface EventsMessagePayload {
    token?:      string;
    team_id?:    string;
    api_app_id?: string;
    event?:      Event;
    type?:       string;
    event_id?:   string;
    event_time?: number;
}

export interface Event {
    type?:         string;
    channel?:      string;
    user?:         string;
    text?:         string;
    ts?:           string;
    event_ts?:     string;
    channel_type?: string;
    edited?:       Edited;
    subtype?:      string;
    hidden?:       boolean;
    deleted_ts?:   string;
    is_starred?:   boolean;
}

export interface Edited {
    user?: string;
    ts?:   string;
}
