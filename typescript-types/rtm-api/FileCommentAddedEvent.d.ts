export interface FileCommentAddedEvent {
    type?:    string;
    comment?: Comment;
    file_id?: string;
    file?:    File;
}

export interface Comment {
}

export interface File {
    id?: string;
}
