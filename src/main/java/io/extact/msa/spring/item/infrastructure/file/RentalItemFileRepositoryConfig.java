package io.extact.msa.spring.item.infrastructure.file;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import io.extact.msa.spring.item.domain.model.RentalItem;
import io.extact.msa.spring.platform.fw.domain.constraint.ValidationConfiguration;
import io.extact.msa.spring.platform.fw.infrastructure.persistence.file.ModelArrayMapper;
import io.extact.msa.spring.platform.fw.infrastructure.persistence.file.io.FileOperator;
import io.extact.msa.spring.platform.fw.infrastructure.persistence.file.io.LoadPathDeriver;

@Configuration(proxyBeanMethods = false)
@Import(ValidationConfiguration.class)
@Profile("file")
class RentalItemFileRepositoryConfig {

    @Bean
    ModelArrayMapper<RentalItem> rentalItemArrayMapper() {
        return RentalItemArrayMapper.INSTANCE;
    }

    @Bean
    RentalItemFileRepository rentalItemFileRepository(Environment env, ModelArrayMapper<RentalItem> mapper) {
        LoadPathDeriver pathDeriver = new LoadPathDeriver(env);
        FileOperator fileOperator = new FileOperator(pathDeriver.derive(RentalItemFileRepository.FILE_ENTITY));
        return new RentalItemFileRepository(fileOperator, mapper);
    }
}
