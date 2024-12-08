package io.extact.msa.spring.item.web;

import io.extact.msa.spring.item.application.EditRentalItemCommand;
import io.extact.msa.spring.item.application.RegisterRentalItemCommand;
import io.extact.msa.spring.item.domain.model.ItemId;
import lombok.Generated;

class RequestUtils {

    @Generated
    private RequestUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    static RegisterRentalItemCommand toRegisterCommand(AddRentalItemRequest req) {

        return RegisterRentalItemCommand.builder()
                .serialNo(req.serialNo())
                .itemName(req.itemName())
                .build();
    }

    static EditRentalItemCommand toEditCommand(UpdateRentalItemRequest req) {
        return EditRentalItemCommand.builder()
                .id(new ItemId(req.id()))
                .serialNo(req.serialNo())
                .itemName(req.itemName())
                .build();
    }
}
