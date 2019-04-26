export interface ApiTestResponse {
    ok?:    boolean;
    args?:  Args;
    error?: string;
}

export interface Args {
    foo?:   string;
    error?: string;
}
