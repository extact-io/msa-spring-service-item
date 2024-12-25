package io.extact.msa.spring.item.domain.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import io.extact.msa.spring.platform.fw.domain.constraint.ItemName;
import io.extact.msa.spring.platform.fw.domain.constraint.SerialNo;
import io.extact.msa.spring.platform.fw.domain.model.DomainModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(of = "id")
@Getter
@ToString
public class RentalItem implements DomainModel {

    private final @NotNull @Valid ItemId id;
    private @SerialNo String serialNo;
    private @ItemName String itemName;

    public static RentalItem reconstruct(int id, String serialNo, String itemName) {
        return new RentalItem(new ItemId(id), serialNo, itemName);
    }

    public void editSerialNoAndName(String serialNo, String itemName) {
        this.serialNo = serialNo;
        this.itemName = itemName;
    }

    public interface RentalItemCreatable {
        default RentalItem newInstance(ItemId id, String serialNo, String itemName) {
            return new RentalItem(id, serialNo, itemName);
        }
    }
}
