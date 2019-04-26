export interface UsersGetPresenceResponse {
    ok?:       boolean;
    presence?: string;
    error?:    string;
    needed?:   string;
    provided?: string;
}
