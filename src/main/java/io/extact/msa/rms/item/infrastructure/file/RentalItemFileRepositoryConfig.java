package io.extact.msa.rms.item.infrastructure.file;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

import io.extact.msa.rms.item.domain.RentalItemRepository;
import io.extact.msa.rms.item.domain.model.RentalItem;
import io.extact.msa.spring.platform.fw.domain.constraint.ValidationConfiguration;
import io.extact.msa.spring.platform.fw.infrastructure.persistence.file.ModelArrayMapper;
import io.extact.msa.spring.platform.fw.infrastructure.persistence.file.io.FileOperator;
import io.extact.msa.spring.platform.fw.infrastructure.persistence.file.io.LoadPathDeriver;

@Configuration(proxyBeanMethods = false)
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
    @Primary
    RentalItemFileRepository rentalItemFileRepository(FileOperator fileOperator, ModelArrayMapper<RentalItem> mapper) {
        return new RentalItemFileRepository(fileOperator, mapper);
    }

    @Bean
    @Scope("prototype")
    @Qualifier("prototype") // for unit test
    RentalItemRepository prototypePersonFileRepository(Environment env) throws IOException {
        FileOperator fileOperator = fileOperator(env); // Bean生成の都度ファイルを再配置する
        return new RentalItemFileRepository(fileOperator, rentalItemArrayMapper());
    }
}
