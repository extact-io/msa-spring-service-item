package io.extact.msa.spring.item;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

import io.extact.msa.spring.item.domain.RentalItemRepository;
import io.extact.msa.spring.item.domain.model.RentalItem;
import io.extact.msa.spring.platform.core.auth.configure.AuthorizeHttpRequestCustomizer;
import io.extact.msa.spring.platform.core.auth.header.RmsHeaderAuthConfig;
import io.extact.msa.spring.platform.fw.RmsFrameworkConfig;
import io.extact.msa.spring.platform.fw.domain.service.DuplicateChecker;
import io.extact.msa.spring.platform.fw.domain.service.SimpleDuplicateChecker;

@Configuration(proxyBeanMethods = false)
@Import({ RmsFrameworkConfig.class, RmsHeaderAuthConfig.class })
public class AppConfig {

    @Bean
    DuplicateChecker<RentalItem> duplicateChecker(RentalItemRepository repository) {
        return new SimpleDuplicateChecker<RentalItem>(repository);
    }

    @Bean
    AuthorizeHttpRequestCustomizer authorizeRequestCustomizer() {
        return (AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry configurer) -> configurer
                .anyRequest().authenticated();
    }
}
