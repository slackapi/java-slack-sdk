export interface RTMFileSharedEvent {
    type?:    string;
    file_id?: string;
    file?:    File;
}

export interface File {
    id?: string;
}
