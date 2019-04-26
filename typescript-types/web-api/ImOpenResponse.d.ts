export interface ImOpenResponse {
    ok?:       boolean;
    channel?:  Channel;
    error?:    string;
    needed?:   string;
    provided?: string;
}

export interface Channel {
    id?: string;
}
