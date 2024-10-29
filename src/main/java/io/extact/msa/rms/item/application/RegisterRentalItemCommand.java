package io.extact.msa.rms.item.application;

import lombok.NonNull;

public record RegisterRentalItemCommand(
        @NonNull String serialNo,
        @NonNull String itemName) {
}
