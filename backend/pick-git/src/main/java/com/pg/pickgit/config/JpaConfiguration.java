package com.pg.pickgit.config;

import com.pg.pickgit.user.domain.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackageClasses = {UserRepository.class})
@EnableJpaAuditing
@Configuration
public class JpaConfiguration {
}
