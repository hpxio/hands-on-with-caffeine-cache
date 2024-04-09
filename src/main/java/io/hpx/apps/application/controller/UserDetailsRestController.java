package io.hpx.apps.application.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.hpx.apps.application.model.UserDetails;
import io.hpx.apps.application.model.request.UserDetailsRequest;
import io.hpx.apps.application.model.response.UserDetailsResponse;
import io.hpx.apps.application.service.UserDetailsService;
import io.hpx.apps.application.util.UuidGenerator;

@RestController
@RequestMapping("/app/v1/user")
public class UserDetailsRestController {

  private final UserDetailsService userDetailsService;

  public UserDetailsRestController(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @GetMapping("/users")
  public List<UserDetails> getAllUsers() {
    return userDetailsService.getUserDetails();
  }

  @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
  public String createUser(@RequestBody UserDetailsRequest userDetailsRequest) {
    /* UserDetails act as DTO here, we will use UDT in caching later
     * Ideally this should be in transaction or validation layer, but
     * I have kept here, since it is a small process and focus is on caching */
    UserDetails userDetails = UserDetails
        .builder()
        .active(true)
        .age(userDetailsRequest.age())
        .uuid(UuidGenerator.generate())
        .userName(userDetailsRequest.userName())
        .userNickName(userDetailsRequest.userNickName())
        .build();

    /* calling service to persist data */
    return userDetailsService.createUserDetails(userDetails);
  }

  @GetMapping("/{uuid}/status")
  public UserDetailsResponse getUserStatus(@PathVariable("uuid") String uuid) {
    return userDetailsService.statusUSerDetails(uuid);
  }
}
