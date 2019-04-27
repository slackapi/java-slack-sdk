export interface EventsResourcesRemovedPayload {
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
    resources?: ResourceElement[];
}

export interface ResourceElement {
    resource?: ResourceResource;
    scopes?:   string[];
}

export interface ResourceResource {
    type?:  string;
    grant?: Grant;
}

export interface Grant {
    type?:        string;
    resource_id?: string;
}
