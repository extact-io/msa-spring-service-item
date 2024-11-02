package io.extact.msa.spring.item.web;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.extact.msa.spring.item.application.EditRentalItemCommand;
import io.extact.msa.spring.item.application.RegisterRentalItemCommand;
import io.extact.msa.spring.item.application.RentalItemApplicationService;
import io.extact.msa.spring.item.domain.model.ItemId;
import io.extact.msa.spring.platform.fw.domain.constraint.RmsId;
import io.extact.msa.spring.platform.fw.web.RmsRestController;
import lombok.RequiredArgsConstructor;

@RmsRestController("/items")
@RequiredArgsConstructor
public class RentalItemController {

    private final RentalItemApplicationService service;

    @GetMapping
    public List<RentalItemResponse> getAll() {
        return service.getAll().stream()
                .map(RentalItemResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public RentalItemResponse get(@RmsId @PathVariable("id") Integer itemId) {
        return service.getById(new ItemId(itemId))
                .map(RentalItemResponse::from)
                .orElse(null);
    }

    @PostMapping
    public RentalItemResponse add(@Valid @RequestBody AddRentalItemRequest request) {
        RegisterRentalItemCommand command = request.transform(this::toRegisterCommand);
        return service.register(command)
                .transform(RentalItemResponse::from);
    }

    @PutMapping
    public RentalItemResponse update(@Valid @RequestBody UpdateRentalItemRequest request) {
        EditRentalItemCommand command = request.transform(this::toEditCommand);
        return service.edit(command)
                .transform(RentalItemResponse::from);
    }

    @DeleteMapping("/{id}")
    public void delete(@RmsId @PathVariable("id") Integer itemId) {
        service.delete(new ItemId(itemId));
    }

    @GetMapping("/exists/{id}")
    public boolean exists(@RmsId @PathVariable("id") Integer itemId) {
        return service.getById(new ItemId(itemId)).isPresent();
    }


    private RegisterRentalItemCommand toRegisterCommand(AddRentalItemRequest req) {
        return new RegisterRentalItemCommand(req.serialNo(), req.itemName());
    }

    private EditRentalItemCommand toEditCommand(UpdateRentalItemRequest req) {
        return new EditRentalItemCommand(req.rentalItemId(), req.serialNo(), req.itemName());
    }
}
