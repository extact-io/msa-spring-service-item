package io.extact.msa.spring.item.domain;

import org.springframework.stereotype.Service;

import io.extact.msa.spring.item.domain.model.ItemId;
import io.extact.msa.spring.item.domain.model.RentalItem;
import io.extact.msa.spring.item.domain.model.RentalItem.RentalItemCreatable;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RentalItemFactory implements RentalItemCreatable {

    private final RentalItemRepository repository;

    public RentalItem create(String serialNo, String itemName) {
        return newInstance(new ItemId(repository.nextIdentity()), serialNo, itemName);
    }
}
