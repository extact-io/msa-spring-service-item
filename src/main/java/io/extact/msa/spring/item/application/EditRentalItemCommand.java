package io.extact.msa.spring.item.application;

import io.extact.msa.spring.item.domain.model.ItemId;
import lombok.Builder;

@Builder
public record EditRentalItemCommand(
        ItemId id,
        String serialNo,
        String itemName) {
}
