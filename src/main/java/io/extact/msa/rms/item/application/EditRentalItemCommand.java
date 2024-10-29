package io.extact.msa.rms.item.application;

import io.extact.msa.rms.item.domain.model.ItemId;
import lombok.NonNull;

public record EditRentalItemCommand(
        @NonNull ItemId id,
        @NonNull String serialNo,
        @NonNull String itemName) {
}
