export interface MpimHistoryResponse {
    ok?:       boolean;
    messages?: string[];
    has_more?: boolean;
    error?:    string;
    needed?:   string;
    provided?: string;
}
