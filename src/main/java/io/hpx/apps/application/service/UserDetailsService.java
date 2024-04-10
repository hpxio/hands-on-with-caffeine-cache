package io.hpx.apps.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import io.hpx.apps.application.cache.DefaultUserDetailsCacheService;
import io.hpx.apps.application.model.UserDetails;
import io.hpx.apps.application.model.response.CreateUserDetailsResponse;
import io.hpx.apps.application.model.response.UserDetailsResponse;
import io.hpx.apps.application.repository.UserDetailsEntity;
import io.hpx.apps.application.repository.UserDetailsRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserDetailsService {

  private final UserDetailsRepository repository;

  private final DefaultUserDetailsCacheService cacheService;

  public UserDetailsService(UserDetailsRepository repository, DefaultUserDetailsCacheService cacheService) {
    this.repository = repository;
    this.cacheService = cacheService;
  }

  /* FIXME: list not populated correctly */
  public List<UserDetails> getUserDetails() {
    List<UserDetails> userDetailsList = new ArrayList<>();
    log.info("service request to get all users");
    List<UserDetailsEntity> userDetailsEntityList = repository.findAll();
    log.info("{} users found", userDetailsEntityList.size());


    return userDetailsList;
  }

  public String getUserNickName(String uuid) {
    log.info("service request to get user nickname for '{}'", uuid);
    return cacheService.getUserNickName(uuid);
  }

  public CreateUserDetailsResponse createUserDetails(UserDetails userDetails) {
    log.info("service request to insert new user");
    UserDetailsEntity userDetailsEntity = new UserDetailsEntity();
    BeanUtils.copyProperties(userDetails, userDetailsEntity);
    UserDetailsEntity savedEntity = repository.save(userDetailsEntity);
    log.info("new user '{}' inserted", userDetails.getUserName());
    return new CreateUserDetailsResponse(savedEntity.getUserNickName(), savedEntity.getUuid());
  }

  public UserDetailsResponse statusUserDetails(String uuid) {
    log.info("service request to retrieve user details");
    UserDetailsEntity userDetailsEntity = repository.findByUuid(uuid);
    if (Objects.nonNull(userDetailsEntity)) {
      UserDetailsResponse userDetailsResponse = new UserDetailsResponse(
          userDetailsEntity.getAge(),
          userDetailsEntity.getUserName(),
          userDetailsEntity.getUserNickName(),
          userDetailsEntity.getUuid(),
          userDetailsEntity.isActive());
      log.info("user found for {}", uuid);
      return userDetailsResponse;
    } else {
      throw new RuntimeException("no record found for " + uuid);
    }
  }
}
