export interface SubteamMembersChangedEvent {
    type?:                 string;
    subteam_id?:           string;
    team_id?:              string;
    date_previous_update?: number;
    date_update?:          number;
    added_users_count?:    number;
    removed_users_count?:  number;
}
