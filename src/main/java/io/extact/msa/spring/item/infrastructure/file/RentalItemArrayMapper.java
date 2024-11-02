package io.extact.msa.spring.item.infrastructure.file;

import io.extact.msa.spring.item.domain.model.RentalItem;
import io.extact.msa.spring.platform.fw.exception.RmsSystemException;
import io.extact.msa.spring.platform.fw.infrastructure.persistence.file.ModelArrayMapper;

public class RentalItemArrayMapper implements ModelArrayMapper<RentalItem> {

    public static final RentalItemArrayMapper INSTANCE = new RentalItemArrayMapper();

    @Override
    public RentalItem toModel(String[] attributes) throws RmsSystemException {
        Integer id = Integer.parseInt(attributes[0]);
        String serialNo = attributes[1];
        String itemName = attributes[2];
        return RentalItem.reconstruct(id, serialNo, itemName);
    }

    @Override
    public String[] toArray(RentalItem item) {
        String[] attributes = new String[3];
        attributes[0] = String.valueOf(item.getId().id());
        attributes[1] = item.getSerialNo();
        attributes[2] = item.getItemName();
        return attributes;
    }
}
