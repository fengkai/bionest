package com.zkteco.bionest.autoconfigure.data.jpa;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Autoconfigure for jpa.
 *
 * @author Tyler Feng
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean(DataSource.class)
@EnableJpaRepositories
//@EntityScan("com.zkteco.bionest.*.persistence.*")
public class JpaAutoconfiguration {


}
