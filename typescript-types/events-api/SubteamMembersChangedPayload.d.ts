export interface SubteamMembersChangedPayload {
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
    type?:                 string;
    subteam_id?:           string;
    team_id?:              string;
    date_previous_update?: number;
    date_update?:          number;
    added_users?:          string[];
    added_users_count?:    number;
    removed_users?:        string[];
    removed_users_count?:  number;
}
