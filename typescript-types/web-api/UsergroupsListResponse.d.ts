export interface UsergroupsListResponse {
    ok?:         boolean;
    usergroups?: string[];
    error?:      string;
    needed?:     string;
    provided?:   string;
}
