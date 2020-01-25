package com.github.seratch.jslack.api.scim.response;

import com.github.seratch.jslack.api.scim.SCIMApiResponse;
import com.github.seratch.jslack.api.scim.model.User;
import lombok.Data;

@Data
public class UsersPatchResponse extends User implements SCIMApiResponse {
}
