package io.hpx.apps.application.cache;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@CacheConfig(cacheNames = "defaultCacheManager")
public class DefaultUserDetailsCacheService {

  @Cacheable(value = "defaultUserDetailsCache", unless = "#result==null")
  public String getUserNickName(String uuid) {
    return null;
  }
}
