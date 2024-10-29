package io.extact.msa.rms.item.webapi;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Path;

import io.extact.msa.rms.item.application.RentalItemService;
import io.extact.msa.rms.item.webapi.dto.AddRentalItemEventDto;
import io.extact.msa.rms.item.webapi.dto.RentalItemResourceDto;
import io.extact.msa.rms.platform.core.validate.ValidateParam;

@Path("items")
@ApplicationScoped
@ValidateParam
public class RentalItemResourceImpl implements RentalItemResource {

    private RentalItemService itemService;

    @Inject
    public RentalItemResourceImpl(RentalItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public List<RentalItemResourceDto> getAll() {
        return itemService.findAll().stream()
                .map(RentalItemResourceDto::from)
                .toList();
    }

    @Override
    public RentalItemResourceDto get(Integer itemId) {
        return itemService.get(itemId)
                .map(RentalItemResourceDto::from)
                .orElse(null);
    }

    @Override
    public RentalItemResourceDto add(@Valid AddRentalItemEventDto dto) {
        return itemService.add(dto.toEntity())
                .transform(RentalItemResourceDto::from);
    }

    @Override
    public RentalItemResourceDto update(@Valid RentalItemResourceDto dto) {
        return itemService.update(dto.toEntity())
                .transform(RentalItemResourceDto::from);
    }

    @Override
    public void delete(Integer itemId) {
        itemService.delete(itemId);
    }

    @Override
    public boolean exists(Integer itemId) {
        return itemService.get(itemId).isPresent();
    }
}
