package io.hpx.apps.application.cache;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableCaching
public class ApplicationCacheConfig {

  private static final int DEFAULT_MAX_SIZE = 5000;

  private static final int DEFAULT_INITIAL_CAPACITY = 1000;

  private static final long DEFAULT_TTL_DURATION = 1L;

  @Bean
  @Primary
  public CaffeineCacheManager getPrimaryCacheManager() {
    CaffeineCacheManager cacheManager = new CaffeineCacheManager("defaultCacheManager");
    cacheManager.registerCustomCache("defaultUserDetailsCache", defaultUserDetailsCache());
    return cacheManager;
  }

  public Cache<Object, Object> defaultUserDetailsCache() {
    return Caffeine
        .newBuilder()
        .recordStats()
        .maximumSize(DEFAULT_MAX_SIZE)
        .initialCapacity(DEFAULT_INITIAL_CAPACITY)
        .expireAfterAccess(DEFAULT_TTL_DURATION, TimeUnit.MINUTES).build();
  }
}
