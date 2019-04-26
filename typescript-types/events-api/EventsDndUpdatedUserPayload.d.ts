export interface EventsDndUpdatedUserPayload {
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
    user?:       string;
    dnd_status?: DndStatus;
}

export interface DndStatus {
    dnd_enabled?:       boolean;
    next_dnd_start_ts?: number;
    next_dnd_end_ts?:   number;
}
