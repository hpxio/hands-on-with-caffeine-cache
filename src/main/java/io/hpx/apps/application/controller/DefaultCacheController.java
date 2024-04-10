package io.hpx.apps.application.controller;

import java.util.Collection;

import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.benmanes.caffeine.cache.stats.CacheStats;

import io.hpx.apps.application.cache.ApplicationCacheConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/app/v1/caches")
public class DefaultCacheController {

  private final ApplicationCacheConfig cacheConfig;

  public DefaultCacheController(ApplicationCacheConfig cacheConfig) {
    this.cacheConfig = cacheConfig;
  }

  @GetMapping
  public Collection<String> getCacheItems() {
    return cacheConfig.getPrimaryCacheManager().getCacheNames();
  }

  /* FIXME: no output shown in response */
  @GetMapping(value = "/stats")
  public CacheStats getCacheStats(@RequestParam String cacheName) {
    CaffeineCache cache = (CaffeineCache) cacheConfig.getPrimaryCacheManager().getCache(cacheName);
    CacheStats stats = cache.getNativeCache().stats();
    log.info("Stats for cache '{}', {}", cacheName, stats);
    return stats;
  }
}
