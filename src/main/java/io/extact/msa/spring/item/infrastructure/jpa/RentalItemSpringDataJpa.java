package io.extact.msa.spring.item.infrastructure.jpa;

import java.util.Optional;

import io.extact.msa.spring.platform.fw.infrastructure.persistence.jpa.SpringDataJpaExecutor;
import lombok.NonNull;

public interface RentalItemSpringDataJpa extends SpringDataJpaExecutor<RentalItemEntity> {

    Optional<RentalItemEntity> findBySerialNo(@NonNull String serialNo);
}
