export interface AuthRevokeResponse {
    ok?:       boolean;
    error?:    string;
    needed?:   string;
    provided?: string;
}
