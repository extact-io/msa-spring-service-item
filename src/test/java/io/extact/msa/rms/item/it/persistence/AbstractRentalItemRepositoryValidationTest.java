package io.extact.msa.rms.item.it.persistence;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.extact.msa.rms.test.junit5.JulToSLF4DelegateExtension;
import io.extact.msa.spring.item.domain.model.RentalItem;
import io.extact.msa.spring.item.persistence.RentalItemRepository;

import jakarta.enterprise.inject.spi.CDI;
import jakarta.validation.ConstraintViolationException;

@ExtendWith(JulToSLF4DelegateExtension.class)
abstract class AbstractRentalItemRepositoryValidationTest {

    @Test
    void testAddValidate() {
        var repo = CDI.current().select(RentalItemRepository.class).get();
        var thrown = catchThrowable(() -> repo.add(new RentalItem()));
        assertThat(thrown).isInstanceOf(ConstraintViolationException.class);
        assertThat(((ConstraintViolationException) thrown).getConstraintViolations()).hasSize(1);
    }

    @Test
    void testUpdateValidate() {
        var repo = CDI.current().select(RentalItemRepository.class).get();
        var thrown = catchThrowable(() -> repo.update(new RentalItem()));
        assertThat(thrown).isInstanceOf(ConstraintViolationException.class);
        assertThat(((ConstraintViolationException) thrown).getConstraintViolations()).hasSize(2);
    }
}
