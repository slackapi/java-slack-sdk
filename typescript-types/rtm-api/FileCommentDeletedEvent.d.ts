export interface FileCommentDeletedEvent {
    type?:    string;
    comment?: string;
    file_id?: string;
    file?:    File;
}

export interface File {
    id?: string;
}
