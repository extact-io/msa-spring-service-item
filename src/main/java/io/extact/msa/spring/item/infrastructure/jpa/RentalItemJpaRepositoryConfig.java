package io.extact.msa.spring.item.infrastructure.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.extact.msa.spring.platform.fw.domain.constraint.ValidationConfiguration;
import io.extact.msa.spring.platform.fw.infrastructure.persistence.jpa.DefaultModelEntityMapper;

@Configuration(proxyBeanMethods = false)
@EntityScan(basePackageClasses = RentalItemEntity.class)
@EnableJpaRepositories(basePackageClasses = RentalItemSpringDataJpa.class)
@Import(ValidationConfiguration.class)
@Profile("jpa")
class RentalItemJpaRepositoryConfig {

    @Bean
    RentalItemJpaRepository rentalItemJpaRepository(RentalItemSpringDataJpa springData) {
        return new RentalItemJpaRepository(
                springData,
                new DefaultModelEntityMapper<>(RentalItemEntity::from));
    }
}
