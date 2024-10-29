package io.extact.msa.rms.item.domain;

import io.extact.msa.rms.item.domain.model.RentalItem;
import io.extact.msa.spring.platform.fw.domain.service.DuplicationDataFinder;
import io.extact.msa.spring.platform.fw.infrastructure.persistence.GenericRepository;

public interface RentalItemRepository extends GenericRepository<RentalItem>, DuplicationDataFinder<RentalItem> {
}
