export interface TeamBillableInfoResponse {
    ok?:            boolean;
    billable_info?: { [key: string]: BillableInfo };
    error?:         string;
    needed?:        string;
    provided?:      string;
}

export interface BillableInfo {
    billing_active?: boolean;
}
