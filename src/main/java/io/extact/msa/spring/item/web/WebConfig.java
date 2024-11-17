package io.extact.msa.spring.item.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

import io.extact.msa.spring.platform.core.auth.configure.AuthorizeHttpRequestCustomizer;
import io.extact.msa.spring.platform.core.auth.header.RmsHeaderAuthConfig;
import io.extact.msa.spring.platform.fw.web.RestControllerConfig;

@Configuration(proxyBeanMethods = false)
@Import({ RestControllerConfig.class, RmsHeaderAuthConfig.class })
class WebConfig {

    @Bean
    AuthorizeHttpRequestCustomizer authorizeRequestCustomizer() {
        return (AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry configurer) -> configurer
                .anyRequest().authenticated();
    }
}
