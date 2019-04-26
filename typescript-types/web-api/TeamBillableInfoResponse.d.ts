export interface TeamBillableInfoResponse {
    ok?:            boolean;
    billable_info?: BillableInfo;
}

export interface BillableInfo {
    U03E94MK0?: U03E94Mk0;
}

export interface U03E94Mk0 {
    billing_active?: boolean;
}
