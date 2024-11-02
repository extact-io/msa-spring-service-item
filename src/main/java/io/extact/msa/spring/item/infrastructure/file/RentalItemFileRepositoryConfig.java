package io.extact.msa.spring.item.infrastructure.file;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import io.extact.msa.spring.item.domain.model.RentalItem;
import io.extact.msa.spring.platform.core.condition.EnableAutoConfigurationWithoutJpa;
import io.extact.msa.spring.platform.fw.domain.constraint.ValidationConfiguration;
import io.extact.msa.spring.platform.fw.infrastructure.persistence.file.ModelArrayMapper;
import io.extact.msa.spring.platform.fw.infrastructure.persistence.file.io.FileOperator;
import io.extact.msa.spring.platform.fw.infrastructure.persistence.file.io.LoadPathDeriver;

@Configuration(proxyBeanMethods = false)
@Profile("file")
@EnableAutoConfigurationWithoutJpa
@Import(ValidationConfiguration.class)
public class RentalItemFileRepositoryConfig {

    @Bean
    FileOperator fileOperator(Environment env) throws IOException {
        LoadPathDeriver pathDeriver = new LoadPathDeriver(env);
        return new FileOperator(pathDeriver.derive(RentalItemFileRepository.FILE_ENTITY));
    }

    @Bean
    ModelArrayMapper<RentalItem> rentalItemArrayMapper() {
        return RentalItemArrayMapper.INSTANCE;
    }

    @Bean
    RentalItemFileRepository rentalItemFileRepository(FileOperator fileOperator, ModelArrayMapper<RentalItem> mapper) {
        return new RentalItemFileRepository(fileOperator, mapper);
    }
}
