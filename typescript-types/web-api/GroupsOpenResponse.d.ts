export interface GroupsOpenResponse {
    ok?:           boolean;
    no_op?:        boolean;
    already_open?: boolean;
    error?:        string;
    needed?:       string;
    provided?:     string;
}
