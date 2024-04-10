package io.hpx.apps.application.cache;

import java.util.List;

import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import io.hpx.apps.application.repository.UserDetailsEntity;
import io.hpx.apps.application.repository.UserDetailsRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@CacheConfig(cacheNames = "defaultUserDetailsCache")
public class DefaultUserDetailsCacheService {
  /* implementation of a write-aside cache */

  private final UserDetailsRepository repository;
  private final ApplicationCacheConfig cacheConfig;

  public DefaultUserDetailsCacheService(UserDetailsRepository repository,
      ApplicationCacheConfig cacheConfig) {
    this.repository = repository;
    this.cacheConfig = cacheConfig;
  }

  @PostConstruct
  public void init() {
    log.info("Initializing DefaultUserDetailsCacheService");
    loadNickNameFromDb();
  }

  private void loadNickNameFromDb() {
    Cache cache = cacheConfig.getPrimaryCacheManager().getCache("defaultUserDetailsCache");
    List<UserDetailsEntity> userDetailsEntityList = repository.findAll();
    for (UserDetailsEntity userDetailsEntity : userDetailsEntityList) {
      cache.put(userDetailsEntity.getUuid(), userDetailsEntity.getUserNickName());
    }
  }

  @Cacheable(value = "defaultUserDetailsCache", unless = "#result==null")
  public String getUserNickName(String uuid) {
    /* this logger should only be printed if there is a cache miss */
    log.info("'{}' not found in cache, retrieving from DB", uuid);
    return repository.findByUuid(uuid).getUserNickName();
  }
}
