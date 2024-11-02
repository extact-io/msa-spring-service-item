package io.extact.msa.rms.item.it.service;

import static io.extact.msa.rms.test.assertj.ToStringAssert.*;
import static org.assertj.core.api.Assertions.*;

import jakarta.inject.Inject;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.extact.msa.spring.item.application.RentalItemService;
import io.extact.msa.spring.item.domain.model.RentalItem;

@TestMethodOrder(OrderAnnotation.class)
abstract class AbstractRentalItemServiceCrudTest {

    @Inject
    private RentalItemService itemService;

    @Test
    @Order(1)
    void testGet() {
        var expected = RentalItem.of(1, "A0001", "レンタル品1号");
        var actual = itemService.get(1).get();
        assertThatToString(actual).isEqualTo(expected);
    }

    @Test
    @Order(2)
    void testFindAll() {
        var actual = itemService.findAll();
        assertThat(actual).hasSize(4);
    }

    @Test
    @Order(3)
    void testUpate() {
        var expected = RentalItem.of(3, "updateSN", "updateName");
        var updateItem = itemService.get(3).get();
        updateItem.setSerialNo("updateSN");
        updateItem.setItemName("updateName");
        var actual = itemService.update(updateItem);
        assertThatToString(actual).isEqualTo(expected);
    }

    @Test
    @Order(4)
    void testAdd() {
        var addItem = RentalItem.ofTransient("A0005", "レンタル品5号");
        var actual = itemService.add(addItem);
        var expected = RentalItem.of(5, "A0005", "レンタル品5号");
        assertThatToString(actual).isEqualTo(expected);
    }

    @Test
    @Order(5)
    void testDelete() {
        itemService.delete(5);
        var actual = itemService.findAll();
        assertThat(actual).hasSize(4);
    }
}
