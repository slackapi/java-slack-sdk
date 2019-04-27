export interface DndTeamInfoResponse {
    ok?:       boolean;
    users?:    { [key: string]: User };
    error?:    string;
    needed?:   string;
    provided?: string;
}

export interface User {
    dnd_enabled?:       boolean;
    next_dnd_start_ts?: number;
    next_dnd_end_ts?:   number;
}
