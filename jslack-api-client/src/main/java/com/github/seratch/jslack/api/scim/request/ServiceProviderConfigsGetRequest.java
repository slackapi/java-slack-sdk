package com.github.seratch.jslack.api.scim.request;

import com.github.seratch.jslack.api.scim.SCIMApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceProviderConfigsGetRequest implements SCIMApiRequest {
    private String token;
}
