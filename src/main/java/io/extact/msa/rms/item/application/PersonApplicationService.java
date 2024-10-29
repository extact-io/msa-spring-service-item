package io.extact.msa.rms.item.application;

import org.springframework.transaction.annotation.Transactional;

import io.extact.msa.rms.item.domain.RentalItemFactory;
import io.extact.msa.rms.item.domain.RentalItemRepository;
import io.extact.msa.rms.item.domain.model.RentalItem;
import io.extact.msa.spring.platform.fw.application.ApplicationServiceSupport;
import io.extact.msa.spring.platform.fw.domain.service.DuplicateChecker;
import io.extact.msa.spring.platform.fw.exception.BusinessFlowException;
import io.extact.msa.spring.platform.fw.exception.BusinessFlowException.CauseType;
import io.extact.msa.spring.platform.fw.infrastructure.persistence.GenericRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
public class PersonApplicationService implements ApplicationServiceSupport<RentalItem> {

    private final RentalItemFactory factory;
    private final DuplicateChecker<RentalItem> duplicateChecker;
    private final RentalItemRepository repository;

    public RentalItem register(RegisterRentalItemCommand command) {
        RentalItem item = factory.create(command.serialNo(), command.itemName());
        duplicateChecker.check(item);
        repository.add(item);
        return item;
    }

    public RentalItem edit(EditRentalItemCommand command) {
        RentalItem item = repository.find(command.id())
                .orElseThrow(() -> new BusinessFlowException("target does not exist for id", CauseType.NOT_FOUND));
        item.editSerialNoAndName(command.serialNo(), command.itemName());
        duplicateChecker.check(item);
        repository.update(item);
        return item;
    }

    public GenericRepository<RentalItem> getRepository() {
        return this.repository;
    }
}
