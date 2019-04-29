export interface ReactionRemovedPayload {
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
    type?:      string;
    user?:      string;
    reaction?:  string;
    item_user?: string;
    item?:      Item;
    event_ts?:  string;
}

export interface Item {
    type?:         string;
    channel?:      string;
    ts?:           string;
    file?:         string;
    file_comment?: string;
}
