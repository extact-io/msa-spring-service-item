package io.extact.msa.spring.item.application;

import io.extact.msa.spring.item.domain.model.ItemId;
import lombok.NonNull;

public record EditRentalItemCommand(
        @NonNull ItemId id,
        @NonNull String serialNo,
        @NonNull String itemName) {
}
