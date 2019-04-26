export interface DndTeamInfoResponse {
    ok?:    boolean;
    users?: Users;
}

export interface Users {
    U03E94MK0?: U03E94Mk0;
}

export interface U03E94Mk0 {
    dnd_enabled?:       boolean;
    next_dnd_start_ts?: number;
    next_dnd_end_ts?:   number;
}
