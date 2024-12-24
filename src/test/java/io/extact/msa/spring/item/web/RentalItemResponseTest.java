package io.extact.msa.spring.item.web;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.extact.msa.spring.item.domain.model.RentalItem;

class RentalItemResponseTest {

    @Test
    void from_shouldMapFieldsCorrectly() {

        // Arrange
        RentalItem model = RentalItem.reconstruct(1, "12345", "Test Item");

        // Act
        RentalItemResponse response = RentalItemResponse.from(model);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1);
        assertThat(response.serialNo()).isEqualTo("12345");
        assertThat(response.itemName()).isEqualTo("Test Item");
    }

    @Test
    void from_shouldReturnNullWhenModelIsNull() {
        // Arrange
        RentalItem model = null;

        // Act
        RentalItemResponse response = RentalItemResponse.from(model);

        // Assert
        assertThat(response).isNull();
    }
}
