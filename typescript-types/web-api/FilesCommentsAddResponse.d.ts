export interface FilesCommentsAddResponse {
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
