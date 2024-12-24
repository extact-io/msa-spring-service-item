package io.extact.msa.spring.item.infrastructure.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import io.extact.msa.spring.item.domain.RentalItemRepository;
import io.extact.msa.spring.item.infrastructure.AbstractRentalItemRepositoryTest;

@DataJpaTest
@ActiveProfiles("jpa")
class RentalItemJpaRepositoryTest extends AbstractRentalItemRepositoryTest {

    @Autowired
    private RentalItemRepository repository;

    @Configuration(proxyBeanMethods = false)
    @Import(RentalItemJpaRepositoryConfig.class)
    static class TestConfig {
    }

    @Override
    protected RentalItemRepository repository() {
        return this.repository;
    }
}
