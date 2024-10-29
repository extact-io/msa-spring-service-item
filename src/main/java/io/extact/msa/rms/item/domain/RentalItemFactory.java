package io.extact.msa.rms.item.domain;

import io.extact.msa.rms.item.domain.model.ItemId;
import io.extact.msa.rms.item.domain.model.RentalItem;
import io.extact.msa.rms.item.domain.model.RentalItem.RentalItemCreatable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RentalItemFactory implements RentalItemCreatable {

    private final RentalItemRepository repository;

    public RentalItem create(String serialNo, String itemName) {
        return newInstance(new ItemId(repository.nextIdentity()), serialNo, itemName);
    }
}
