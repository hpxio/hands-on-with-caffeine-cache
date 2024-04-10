package io.hpx.apps.application.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateUserDetailsResponse(@JsonProperty("user_nick_name") String user_nick_name,
                                        @JsonProperty("user_uuid") String user_uuid) {
}
