export interface EventsFileCreatedPayload {
    token?:      string;
    team_id?:    string;
    api_app_id?: string;
    event?:      Event;
    type?:       string;
    event_id?:   string;
    event_time?: number;
}

export interface Event {
    type?:    string;
    file_id?: string;
    file?:    File;
}

export interface File {
    id?: string;
}
