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
    cacheManager.registerCustomCache("statEnabledUserDetailsCache", statEnabledUserDetailsCache());
    return cacheManager;
  }

  private Cache<Object, Object> defaultUserDetailsCache() {
    return Caffeine.newBuilder() // build instance of cache
        .recordStats() // enable cache metrics & run-time stats
        .maximumSize(DEFAULT_MAX_SIZE) // maximum number of objects allowed
        .initialCapacity(DEFAULT_INITIAL_CAPACITY) // default initial allowed objects
        .expireAfterAccess(DEFAULT_TTL_DURATION, TimeUnit.MINUTES).build(); // eviction based on TTL
  }

  private Cache<Object, Object> statEnabledUserDetailsCache() {
    return Caffeine.newBuilder().recordStats() // enable cache metrics & run-time stats
        .maximumSize(DEFAULT_MAX_SIZE) // maximum number of objects allowed
        .initialCapacity(DEFAULT_INITIAL_CAPACITY) // default initial allowed objects
        .expireAfterAccess(DEFAULT_TTL_DURATION, TimeUnit.MINUTES).build();// eviction based on TTL
  }
}
