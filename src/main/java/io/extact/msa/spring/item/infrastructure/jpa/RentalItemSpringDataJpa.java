package io.extact.msa.spring.item.infrastructure.jpa;

import java.util.Optional;

import io.extact.msa.spring.platform.fw.infrastructure.persistence.jpa.JpaRepositoryDelegator;

public interface RentalItemSpringDataJpa extends JpaRepositoryDelegator<RentalItemEntity> {

    Optional<RentalItemEntity> findBySerialNo(String serialNo);
}
