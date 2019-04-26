export interface RTMChannelJoinedEvent {
    type?:    string;
    channel?: Channel;
}

export interface Channel {
    id?:      string;
    name?:    string;
    created?: number;
    creator?: string;
}
