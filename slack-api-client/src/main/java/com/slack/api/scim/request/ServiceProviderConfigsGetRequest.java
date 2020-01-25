package com.slack.api.scim.request;

import com.slack.api.scim.SCIMApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceProviderConfigsGetRequest implements SCIMApiRequest {
    private String token;
}
