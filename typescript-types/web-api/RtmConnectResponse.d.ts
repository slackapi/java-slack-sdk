export interface RtmConnectResponse {
    ok?:       boolean;
    url?:      string;
    team?:     Team;
    self?:     Self;
    error?:    string;
    needed?:   string;
    provided?: string;
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
