package io.extact.msa.spring.item.infrastructure.file;

import java.util.Optional;

import io.extact.msa.spring.item.domain.RentalItemRepository;
import io.extact.msa.spring.item.domain.model.RentalItem;
import io.extact.msa.spring.platform.fw.infrastructure.persistence.file.AbstractFileRepository;
import io.extact.msa.spring.platform.fw.infrastructure.persistence.file.ModelArrayMapper;
import io.extact.msa.spring.platform.fw.infrastructure.persistence.file.io.FileOperator;
import lombok.NonNull;

public class RentalItemFileRepository extends AbstractFileRepository<RentalItem> implements RentalItemRepository {

    static final String FILE_ENTITY = "rental-item";

    public RentalItemFileRepository(FileOperator fileReadWriter, ModelArrayMapper<RentalItem> mapper) {
        super(fileReadWriter, mapper);
    }

    @Override
    public String getEntityName() {
        return FILE_ENTITY;
    }

    @Override
    public Optional<RentalItem> findDuplicationData(@NonNull RentalItem checkItem) {
        return this.findAll().stream()
                .filter(item -> item.getSerialNo().equals(checkItem.getSerialNo()))
                .findFirst();
    }
}
