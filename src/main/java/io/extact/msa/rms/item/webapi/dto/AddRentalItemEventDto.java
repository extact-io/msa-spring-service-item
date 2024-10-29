package io.extact.msa.rms.item.webapi.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import io.extact.msa.rms.item.domain.model.RentalItem;
import io.extact.msa.rms.platform.fw.domain.constraint.ItemName;
import io.extact.msa.rms.platform.fw.domain.constraint.SerialNo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "レンタル品登録イベントDTO")
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter @Setter
public class AddRentalItemEventDto {

    @Schema(required = true)
    @SerialNo
    private String serialNo;

    @Schema(required = false)
    @ItemName
    private String itemName;

    public RentalItem toEntity() {
        return RentalItem.ofTransient(serialNo, itemName);
    }
}
