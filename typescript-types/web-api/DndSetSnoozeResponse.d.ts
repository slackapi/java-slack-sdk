export interface DndSetSnoozeResponse {
    ok?:               boolean;
    error?:            string;
    snooze_enabled?:   boolean;
    snooze_endtime?:   number;
    snooze_remaining?: number;
}
