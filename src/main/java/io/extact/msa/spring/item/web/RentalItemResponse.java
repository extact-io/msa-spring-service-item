package io.extact.msa.spring.item.web;

import io.extact.msa.spring.item.domain.model.RentalItem;

public record RentalItemResponse(

        Integer id,
        String serialNo,
        String itemName) {

    static RentalItemResponse from(RentalItem model) {
        if (model == null) {
            return null;
        }
        return new RentalItemResponse(model.getId().id(), model.getSerialNo(), model.getItemName());
    }
}
