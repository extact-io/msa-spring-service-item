package io.extact.msa.spring.item.application;

import lombok.Builder;

@Builder
public record RegisterRentalItemCommand(
        String serialNo,
        String itemName) {
}
