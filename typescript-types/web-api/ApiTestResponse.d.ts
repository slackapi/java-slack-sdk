export interface ApiTestResponse {
    ok?:       boolean;
    args?:     Args;
    error?:    string;
    needed?:   string;
    provided?: string;
}

export interface Args {
    foo?:   string;
    error?: string;
}
