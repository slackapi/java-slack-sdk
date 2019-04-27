export interface EventsTokensRevokedPayload {
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
    type?:   string;
    tokens?: Tokens;
}

export interface Tokens {
    oauth?: string[];
    bot?:   string[];
}
