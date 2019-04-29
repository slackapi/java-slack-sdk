export interface MemberLeftChannelPayload {
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
    type?:         string;
    user?:         string;
    channel?:      string;
    channel_type?: string;
    team?:         string;
}
