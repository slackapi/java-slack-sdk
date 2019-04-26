export interface OauthTokenResponse {
    ok?:       boolean;
    error?:    string;
    needed?:   string;
    provided?: string;
}
