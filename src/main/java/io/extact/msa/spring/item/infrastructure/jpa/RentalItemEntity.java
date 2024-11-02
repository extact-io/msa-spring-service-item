package io.extact.msa.spring.item.infrastructure.jpa;

import static jakarta.persistence.AccessType.*;

import jakarta.persistence.Access;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import io.extact.msa.spring.item.domain.model.RentalItem;
import io.extact.msa.spring.platform.fw.domain.constraint.ItemName;
import io.extact.msa.spring.platform.fw.domain.constraint.RmsId;
import io.extact.msa.spring.platform.fw.domain.constraint.SerialNo;
import io.extact.msa.spring.platform.fw.infrastructure.persistence.jpa.TableEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Access(FIELD)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class RentalItemEntity implements TableEntity<RentalItem> {

    @Id @RmsId
    private Integer id;
    @SerialNo
    private String serialNo;
    @ItemName
    private String itemName;

    public static RentalItemEntity from(RentalItem model) {
        return new RentalItemEntity(model.getId().id(), model.getSerialNo(), model.getItemName());
    }

    @Override
    public RentalItem toModel() {
        return RentalItem.reconstruct(this.id, this.serialNo, this.itemName);
    }
}
