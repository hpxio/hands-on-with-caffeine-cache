package io.hpx.apps.application.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record UserDetailsRequest(@JsonProperty("user_age") int age, @JsonProperty("user_name") String userName,
                                 @JsonProperty("user_nick_name") String userNickName) {

}
