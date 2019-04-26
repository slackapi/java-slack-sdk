export interface RTMChannelCreatedEvent {
    type?:    string;
    channel?: Channel;
}

export interface Channel {
    id?:      string;
    name?:    string;
    created?: number;
    creator?: string;
}
