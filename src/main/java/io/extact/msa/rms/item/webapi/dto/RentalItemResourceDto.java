package io.extact.msa.rms.item.webapi.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import io.extact.msa.rms.item.domain.model.RentalItem;
import io.extact.msa.rms.platform.fw.domain.constraint.ItemName;
import io.extact.msa.rms.platform.fw.domain.constraint.RmsId;
import io.extact.msa.rms.platform.fw.domain.constraint.SerialNo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "レンタル品DTO")
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter @Setter
public class RentalItemResourceDto {

    @Schema(required = true)
    @RmsId
    private Integer id;

    @Schema(required = true)
    @SerialNo
    private String serialNo;

    @Schema(required = false)
    @ItemName
    private String itemName;

    public static RentalItemResourceDto from(RentalItem entity) {
        if (entity == null) {
            return null;
        }
        var dto = new RentalItemResourceDto();
        dto.setId(entity.getId());
        dto.setSerialNo(entity.getSerialNo());
        dto.setItemName(entity.getItemName());
        return dto;
    }

    public RentalItem toEntity() {
        return RentalItem.of(id, serialNo, itemName);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
