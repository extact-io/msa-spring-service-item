package io.extact.msa.spring.item.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.extact.msa.spring.item.domain.model.RentalItem;
import io.extact.msa.spring.platform.fw.domain.service.DuplicateChecker;
import io.extact.msa.spring.platform.fw.domain.service.SimpleDuplicateChecker;

@Configuration(proxyBeanMethods = false)
class DomainConfig {

    @Bean
    DuplicateChecker<RentalItem> duplicateChecker(RentalItemRepository repository) {
        return new SimpleDuplicateChecker<RentalItem>(repository);
    }
}
