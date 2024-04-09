package io.hpx.apps.application.controller;

import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.hpx.apps.application.cache.ApplicationCacheConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/app/v1/cache")
public class DefaultCacheController {

  private final ApplicationCacheConfig cacheConfig;

  public DefaultCacheController(ApplicationCacheConfig cacheConfig) {
    this.cacheConfig = cacheConfig;
  }

  @GetMapping
  public Collection<String> getCacheItems() {
    return cacheConfig.getPrimaryCacheManager().getCacheNames();
  }
}
