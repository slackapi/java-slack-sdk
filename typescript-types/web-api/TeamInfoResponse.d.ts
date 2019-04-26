export interface TeamInfoResponse {
    ok?:   boolean;
    team?: Team;
}

export interface Team {
    id?:           string;
    name?:         string;
    domain?:       string;
    email_domain?: string;
    icon?:         Icon;
}

export interface Icon {
    image_34?:       string;
    image_44?:       string;
    image_68?:       string;
    image_88?:       string;
    image_102?:      string;
    image_132?:      string;
    image_230?:      string;
    image_original?: string;
}
