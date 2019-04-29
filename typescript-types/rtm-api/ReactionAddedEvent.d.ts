export interface ReactionAddedEvent {
    type?:      string;
    user?:      string;
    reaction?:  string;
    item_user?: string;
    item?:      Item;
    event_ts?:  string;
}

export interface Item {
    type?:         string;
    channel?:      string;
    ts?:           string;
    file?:         string;
    file_comment?: string;
}
