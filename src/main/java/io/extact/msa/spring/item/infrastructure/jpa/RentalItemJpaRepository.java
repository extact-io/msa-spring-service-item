package io.extact.msa.spring.item.infrastructure.jpa;

import java.util.Optional;

import io.extact.msa.spring.item.domain.RentalItemRepository;
import io.extact.msa.spring.item.domain.model.RentalItem;
import io.extact.msa.spring.platform.fw.infrastructure.ModelEntityMapper;
import io.extact.msa.spring.platform.fw.infrastructure.persistence.jpa.AbstractJpaRepository;

public class RentalItemJpaRepository extends AbstractJpaRepository<RentalItem, RentalItemEntity>
        implements RentalItemRepository {

    private final RentalItemSpringDataJpa springJpa;
    private final ModelEntityMapper<RentalItem, RentalItemEntity> entityMapper;

    public RentalItemJpaRepository(RentalItemSpringDataJpa jpa,
            ModelEntityMapper<RentalItem, RentalItemEntity> entityMapper) {
        super(jpa, entityMapper);
        this.springJpa = jpa;
        this.entityMapper = entityMapper;
    }

    @Override
    public Optional<RentalItem> findDuplicationData(RentalItem checkItem) {
        return springJpa.findBySerialNo(checkItem.getSerialNo())
                .map(entityMapper::toModel);
    }
}
