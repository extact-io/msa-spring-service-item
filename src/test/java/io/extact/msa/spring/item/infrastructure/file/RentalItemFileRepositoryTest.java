package io.extact.msa.spring.item.infrastructure.file;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import io.extact.msa.spring.item.domain.RentalItemRepository;
import io.extact.msa.spring.item.domain.model.RentalItem;
import io.extact.msa.spring.item.infrastructure.AbstractRentalItemRepositoryTest;
import io.extact.msa.spring.platform.fw.infrastructure.persistence.file.ModelArrayMapper;
import io.extact.msa.spring.platform.fw.infrastructure.persistence.file.io.FileOperator;
import io.extact.msa.spring.platform.fw.infrastructure.persistence.file.io.LoadPathDeriver;

// TODO TestConfigurationと通常のConfigurationの違いを確認
// TODO @SpringBootTestではなく粒々Beanする作戦を考えてみよう
// TODO 固まったらPersonへ反映
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
//@TestExecutionListeners(listeners = { // 親クラスで定義したトランザクションが開始されないように必要なListenerだけ定義
//        DependencyInjectionTestExecutionListener.class,
//        DirtiesContextTestExecutionListener.class
//})
@ActiveProfiles("file")
class RentalItemFileRepositoryTest extends AbstractRentalItemRepositoryTest {

    private RentalItemRepository repository;

    // ここを起点にするために@Configurationにすると本物の@SpringBootTestでこのクラスも読まれてしまう
    // @TestConfigurationにするとApplicationクラスを起点にされてしまう。
    // うーーん、TestConfigは除外するExcludeFilterかオレオレメタアノテーションを作るか・・
    @Configuration(proxyBeanMethods = false)
    @Import(RentalItemFileRepositoryConfig.class)
    static class TestConfig {

        @Bean("prototype")
        @Scope("prototype")
        @Primary
        RentalItemRepository prototypeRentalItemFileRepository(Environment env,
                ModelArrayMapper<RentalItem> rentalImteArrayMapper) throws IOException {

            // Bean生成の都度ファイルを再配置する
            LoadPathDeriver pathDeriver = new LoadPathDeriver(env);
            FileOperator fileOperator = new FileOperator(pathDeriver.derive(RentalItemFileRepository.FILE_ENTITY));

            return new RentalItemFileRepository(fileOperator, rentalImteArrayMapper);
        }
    }

    // prototypeスコープにして毎回ファイルの初期が行われるようにする
    @BeforeEach
    void beforeEach(@Autowired @Qualifier("prototype") RentalItemRepository repository) {
        this.repository = repository;
    }

    @Override
    protected RentalItemRepository repository() {
        return this.repository;
    }
}
