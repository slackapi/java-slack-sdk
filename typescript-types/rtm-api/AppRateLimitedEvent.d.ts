export interface AppRateLimitedEvent {
    type?:                string;
    token?:               string;
    team_id?:             string;
    minute_rate_limited?: number;
    api_app_id?:          string;
}
