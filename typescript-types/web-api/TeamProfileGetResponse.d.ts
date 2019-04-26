export interface TeamProfileGetResponse {
    ok?:      boolean;
    profile?: Profile;
}

export interface Profile {
    fields?: Field[];
}

export interface Field {
    id?:         string;
    ordering?:   number;
    field_name?: string;
    label?:      string;
    hint?:       string;
    type?:       string;
    is_hidden?:  boolean;
}
