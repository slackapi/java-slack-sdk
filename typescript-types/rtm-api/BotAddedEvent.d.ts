export interface BotAddedEvent {
    type?: string;
    bot?:  Bot;
}

export interface Bot {
    id?:     string;
    app_id?: string;
    name?:   string;
    icons?:  Icons;
}

export interface Icons {
    image_36?: string;
    image_48?: string;
    image_72?: string;
}
