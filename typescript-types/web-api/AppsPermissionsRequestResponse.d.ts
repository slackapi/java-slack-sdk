export interface AppsPermissionsRequestResponse {
    ok?:       boolean;
    error?:    string;
    needed?:   string;
    provided?: string;
}
