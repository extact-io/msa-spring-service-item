package io.extact.msa.spring.item.web;

import io.extact.msa.spring.platform.fw.domain.constraint.ItemName;
import io.extact.msa.spring.platform.fw.domain.constraint.SerialNo;
import io.extact.msa.spring.platform.fw.domain.model.Transformable;

public record AddRentalItemRequest(

        @SerialNo String serialNo,
        @ItemName String itemName) implements Transformable {
}
