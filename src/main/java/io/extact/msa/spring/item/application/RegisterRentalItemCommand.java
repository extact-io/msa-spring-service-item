package io.extact.msa.spring.item.application;

import lombok.NonNull;

public record RegisterRentalItemCommand(
        @NonNull String serialNo,
        @NonNull String itemName) {
}
