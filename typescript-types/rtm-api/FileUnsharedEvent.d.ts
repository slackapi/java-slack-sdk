export interface FileUnsharedEvent {
    type?:    string;
    file_id?: string;
    file?:    File;
}

export interface File {
    id?: string;
}
