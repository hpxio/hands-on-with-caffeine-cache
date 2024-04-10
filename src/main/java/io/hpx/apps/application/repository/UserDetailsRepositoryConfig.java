package io.hpx.apps.application.repository;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"io.hpx.apps.application.repository"})
public class UserDetailsRepositoryConfig {
  /* marker config class, to enable JPA component scanning */
}
