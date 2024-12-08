package io.extact.msa.spring.item.domain.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ItemIdTest {

    @Test
    void testCreateItemIdWithValidId() {
        ItemId itemId = new ItemId(1);
        assertThat(itemId.id()).isEqualTo(1);
    }

    @Test
    void testEqualityForSameValues() {
        ItemId itemId1 = new ItemId(1);
        ItemId itemId2 = new ItemId(1);
        assertThat(itemId1).isEqualTo(itemId2);
    }

    @Test
    void testInequalityForDifferentValues() {
        ItemId itemId1 = new ItemId(1);
        ItemId itemId2 = new ItemId(2);
        assertThat(itemId1).isNotEqualTo(itemId2);
    }

    @Test
    void testToStringDoesNotThrowException() {
        ItemId itemId = new ItemId(1);
        assertThatCode(itemId::toString)
                .doesNotThrowAnyException();
    }
}