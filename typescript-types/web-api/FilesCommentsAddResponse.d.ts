export interface FilesCommentsAddResponse {
    ok?:       boolean;
    comment?:  Comment;
    error?:    string;
    needed?:   string;
    provided?: string;
}

export interface Comment {
    id?:        string;
    created?:   number;
    timestamp?: number;
    user?:      string;
    is_intro?:  boolean;
    comment?:   string;
}
