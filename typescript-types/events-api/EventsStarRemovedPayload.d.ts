export interface EventsStarRemovedPayload {
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
    user?:     string;
    item?:     Item;
    event_ts?: string;
}

export interface Item {
    type?:       string;
    channel?:    string;
    created_by?: string;
    created?:    number;
    message?:    Message;
}

export interface Message {
    client_msg_id?: string;
    type?:          string;
    user?:          string;
    text?:          string;
    ts?:            string;
    permalink?:     string;
}
