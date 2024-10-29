package io.extact.msa.rms.item.service;

import static io.extact.msa.rms.item.RentalItemComponentFactoryTestUtils.*;
import static io.extact.msa.rms.test.assertj.ToStringAssert.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.extact.msa.rms.item.application.RentalItemService;
import io.extact.msa.rms.item.domain.model.RentalItem;
import io.extact.msa.rms.platform.fw.exception.BusinessFlowException;
import io.extact.msa.rms.platform.fw.exception.BusinessFlowException.CauseType;
import io.extact.msa.rms.platform.fw.persistence.file.io.PathResolver;
import io.extact.msa.rms.platform.test.PathResolverParameterExtension;

@ExtendWith(PathResolverParameterExtension.class)
class RentalItemServiceTest {

    private RentalItemService service;

    @BeforeEach
    void setUp(PathResolver pathResolver) throws Exception {
        service = newRentalItemService(pathResolver);
    }

    @Test
    void testGet() {
        var expect = RentalItem.of(1, "A0001", "レンタル品1号");
        var actual = service.get(1).get();
        assertThatToString(actual).isEqualTo(expect);
    }

    @Test
    void testGetNull() {
        var actual = service.get(903); // 存在しないID
        assertThat(actual).isEmpty();
    }

    @Test
    void testFindAll() {
        var expect = List.of(
                RentalItem.of(1, "A0001", "レンタル品1号"),
                RentalItem.of(2, "A0002", "レンタル品2号"),
                RentalItem.of(3, "A0003", "レンタル品3号"),
                RentalItem.of(4, "A0004", "レンタル品4号")
            );

        List<RentalItem> actual = service.findAll();
        assertThatToString(actual).containsExactlyElementsOf(expect);
    }

    @Test
    void testFindBySerialNo() {
        var expect = RentalItem.of(2, "A0002", "レンタル品2号");
        var actual = service.findBySerialNo("A0002");
        assertThatToString(actual).isEqualTo(expect);
    }

    @Test
    void testFindBySerialNoOnNull() {
        var actual = service.findBySerialNo("A9999"); // 存在しないNo
        assertThat(actual).isNull();
    }

    @Test
    void testAdd() throws Exception {
        var r = RentalItem.ofTransient("A0005", "レンタル品5号");
        var expect = RentalItem.of(5, r.getSerialNo(), r.getItemName());
        var actual = service.add(r);
        assertThatToString(actual).isEqualTo(expect);
    }

    @Test
    void testAddOnDuplicate() {
        // "A0004"は既に登録済みのSerialNo
        var addRentalItem = RentalItem.ofTransient("A0004", "レンタル品5号");
        var thrown = catchThrowable(() -> service.add(addRentalItem));
        assertThat(thrown).isInstanceOf(BusinessFlowException.class);
        assertThat(((BusinessFlowException) thrown).getCauseType()).isEqualTo(CauseType.DUPRICATE);
    }

    @Test
    void testUpdate() {
        var update = service.get(1).get();
        update.setItemName("UPDATE");
        var result = service.update(update);

        assertThat(result.getItemName()).isEqualTo("UPDATE");
        assertThatToString(update).isEqualTo(service.get(1).get());
    }

    @Test
    void testUpdateOnNotFound() {
        var update = RentalItem.of(999, null, null);
        var thrown = catchThrowable(() -> service.update(update));
        assertThat(thrown).isInstanceOf(BusinessFlowException.class);
        assertThat(((BusinessFlowException) thrown).getCauseType()).isEqualTo(CauseType.NOT_FOUND);
    }

    @Test
    void testDelete() {
        service.delete(1);
        assertThat(service.get(1)).isEmpty();
    }

    @Test
    void testDeleteOnNotFound() {
        var thrown = catchThrowable(() -> service.delete(999));
        assertThat(thrown).isInstanceOf(BusinessFlowException.class);
        assertThat(((BusinessFlowException) thrown).getCauseType()).isEqualTo(CauseType.NOT_FOUND);
    }
}
