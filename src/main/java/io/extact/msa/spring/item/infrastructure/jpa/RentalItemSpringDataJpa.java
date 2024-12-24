package io.extact.msa.spring.item.infrastructure.jpa;

import java.util.Optional;

import io.extact.msa.spring.platform.fw.infrastructure.persistence.jpa.SpringDataJpaExecutor;

public interface RentalItemSpringDataJpa extends SpringDataJpaExecutor<RentalItemEntity> {

    Optional<RentalItemEntity> findBySerialNo(String serialNo);
}
