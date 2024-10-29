package io.extact.msa.rms.item.domain;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.extact.msa.rms.item.domain.model.RentalItem;
import io.extact.msa.rms.platform.fw.domain.constraint.ItemName;
import io.extact.msa.rms.platform.fw.domain.constraint.RmsId;
import io.extact.msa.rms.platform.fw.domain.constraint.SerialNo;
import io.extact.msa.rms.platform.fw.domain.constraint.ValidationGroups.Update;
import io.extact.msa.rms.test.assertj.ConstraintViolationSetAssert;
import io.extact.msa.rms.test.junit5.ValidatorParameterExtension;
import io.extact.msa.rms.test.utils.PropertyTest;

@ExtendWith(ValidatorParameterExtension.class)
class RentalItemTest extends PropertyTest {

    @Override
    protected Class<?> getTargetClass() {
        return RentalItem.class;
    }

    @Test
    void testSetId() throws Exception {
        RentalItem testee = new RentalItem();
        testee.setId(100);
        Field id = this.getField("id");

        assertThat(id).isNotNull();
        assertThat(id.get(testee)).isEqualTo(100);
    }

    @Test
    void testGetId() throws Exception {
        RentalItem testee = new RentalItem();
        Field id = this.getField("id");

        assertThat(id).isNotNull();

        id.set(testee, 100);
        assertThat(testee.getId()).isEqualTo(100);
    }

    @Test
    void testSetSerialNo() throws Exception {
        RentalItem testee = new RentalItem();
        testee.setSerialNo("A0001");
        Field serialNo = this.getField("serialNo");

        assertThat(serialNo).isNotNull();
        assertThat(serialNo.get(testee)).isEqualTo("A0001");
    }

    @Test
    void testGetSerialNo() throws Exception {
        RentalItem testee = new RentalItem();
        Field serialNo = this.getField("serialNo");

        assertThat(serialNo).isNotNull();

        serialNo.set(testee, "A0001");
        assertThat(testee.getSerialNo()).isEqualTo("A0001");
    }

    @Test
    void testSetItemName() throws Exception {
        RentalItem testee = new RentalItem();
        testee.setItemName("レンタル品");
        Field itemName = this.getField("itemName");

        assertThat(itemName).isNotNull();
        assertThat(itemName.get(testee)).isEqualTo("レンタル品");
    }

    @Test
    void testGetItemName() throws Exception {
        RentalItem testee = new RentalItem();
        Field itemName = this.getField("itemName");

        assertThat(itemName).isNotNull();

        itemName.set(testee, "レンタル品");
        assertThat(testee.getItemName()).isEqualTo("レンタル品");
    }

    @Test
    void testNewInstance() {
        RentalItem testee = RentalItem.of(1, "A0001", "レンタル品");
        assertThat(testee.getId()).isEqualTo(1);
        assertThat(testee.getSerialNo()).isEqualTo("A0001");
        assertThat(testee.getItemName()).isEqualTo("レンタル品");
    }

    @Test
    void testConstraintAnnoteToClass() {
        Class<?>[] expected = {
                RmsId.class,
                SerialNo.class,
                ItemName.class,
        };
        var actual = getFieldAnnotations();
        assertThat(actual).contains(expected);
    }

    @Test
    void testPropetyValidationForUpdate(Validator validator) {

        // エラーがないこと
        RentalItem rentalItem = createAllOKRentalItem();
        Set<ConstraintViolation<RentalItem>> result = validator.validate(rentalItem, Update.class);
        ConstraintViolationSetAssert.assertThat(result)
            .hasNoViolations();

        // IDに-1を設定してテスト
        rentalItem = createAllOKRentalItem();
        rentalItem.setId(-1);
        // Group指定がなし→バリデーションSkip→バリデーションエラーなし
        result = validator.validate(rentalItem);
        ConstraintViolationSetAssert.assertThat(result)
        .hasNoViolations();
        // Groupを指定→バリデーションエラーあり
        result = validator.validate(rentalItem, Update.class);
        ConstraintViolationSetAssert.assertThat(result)
            .hasSize(1)
            .hasViolationOnPath("id")
            .hasMessageEndingWith("Min.message");
    }

    private RentalItem createAllOKRentalItem() {
        RentalItem rentalItem = new RentalItem();
        rentalItem.setId(1);
        rentalItem.setSerialNo("A-0001a");
        rentalItem.setItemName("レンタル品");
        return rentalItem;
    }
}
