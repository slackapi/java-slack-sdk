export interface RTMChannelRenameEvent {
    type?:    string;
    channel?: Channel;
}

export interface Channel {
    id?:      string;
    name?:    string;
    created?: number;
}
