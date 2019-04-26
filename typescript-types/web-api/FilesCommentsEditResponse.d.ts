export interface FilesCommentsEditResponse {
    ok?:      boolean;
    comment?: Comment;
}

export interface Comment {
    id?:        string;
    created?:   number;
    timestamp?: number;
    user?:      string;
    is_intro?:  boolean;
    comment?:   string;
}
