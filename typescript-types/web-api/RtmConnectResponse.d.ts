export interface RtmConnectResponse {
    ok?:   boolean;
    url?:  string;
    team?: Team;
    self?: Self;
}

export interface Self {
    id?:   string;
    name?: string;
}

export interface Team {
    id?:     string;
    name?:   string;
    domain?: string;
}
