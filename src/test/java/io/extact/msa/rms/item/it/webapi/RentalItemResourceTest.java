package io.extact.msa.rms.item.it.webapi;

import static io.extact.msa.rms.platform.test.PlatformTestUtils.*;
import static io.extact.msa.rms.test.assertj.ToStringAssert.*;
import static org.assertj.core.api.Assertions.*;

import java.net.URI;

import jakarta.ws.rs.core.Response.Status;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import io.extact.msa.rms.item.webapi.RentalItemResource;
import io.extact.msa.rms.item.webapi.dto.AddRentalItemEventDto;
import io.extact.msa.rms.item.webapi.dto.RentalItemResourceDto;
import io.extact.msa.rms.platform.core.jaxrs.converter.RmsTypeParameterFeature;
import io.extact.msa.rms.platform.fw.exception.BusinessFlowException;
import io.extact.msa.rms.platform.fw.exception.BusinessFlowException.CauseType;
import io.extact.msa.rms.test.junit5.JulToSLF4DelegateExtension;
import io.extact.msa.rms.test.utils.ClearOpenTelemetryContextCdiExtension;
import io.helidon.microprofile.tests.junit5.AddConfig;
import io.helidon.microprofile.tests.junit5.AddExtension;
import io.helidon.microprofile.tests.junit5.HelidonTest;

@HelidonTest
@AddExtension(ClearOpenTelemetryContextCdiExtension.class)
@AddConfig(key = "server.port", value = "7001")
@ExtendWith(JulToSLF4DelegateExtension.class)
@TestMethodOrder(OrderAnnotation.class)
class RentalItemResourceTest {

    private static final int NO_SIDE_EFFECT = 1; // 副作用なし
    private RentalItemResource itemResource;

    @BeforeEach
    void setup() throws Exception {
        this.itemResource = RestClientBuilder.newBuilder()
                .baseUri(new URI("http://localhost:7001/api/items"))
                .register(RmsTypeParameterFeature.class)
                .build(RentalItemResource.class);
    }

    @Test
    @Order(NO_SIDE_EFFECT)
    void testGetAll() {
        var actual = itemResource.getAll();
        assertThat(actual).hasSize(4);
    }

    @Test
    @Order(NO_SIDE_EFFECT)
    void testGet() {
        var expect = RentalItemResourceDto.of(1, "A0001", "レンタル品1号");
        var actual = itemResource.get(1);
        assertThatToString(actual).isEqualTo(expect);
    }

    @Test
    @Order(NO_SIDE_EFFECT)
    void testGetOnNotFound() {
        var actual = itemResource.get(999);
        assertThat(actual).isNull();
    }

    @Test
    @Order(NO_SIDE_EFFECT)
    void testGetOnParameterError() {
        var thrown = catchThrowable(() -> itemResource.get(-1));
        assertValidationErrorInfo(thrown, 1);
    }

    @Test
    void testAdd() {
        var expect = RentalItemResourceDto.of(5, "A0005", "レンタル品5号");
        var addItem = AddRentalItemEventDto.of("A0005", "レンタル品5号");
        var actual = itemResource.add(addItem);
        assertThatToString(actual).isEqualTo(expect);
    }

    @Test
    @Order(NO_SIDE_EFFECT)
    void testAddOnParameterError() {
        var thrown = catchThrowable(() -> itemResource.add(new AddRentalItemEventDto())); // paramter error
        assertValidationErrorInfo(thrown, 1);
    }

    @Test
    @Order(NO_SIDE_EFFECT)
    void testAddOnDuplicate() {
        var addItem = AddRentalItemEventDto.of("A0004", "レンタル品5号"); // SerialNo重複
        var thrown = catchThrowable(() -> itemResource.add(addItem));
        assertGenericErrorInfo(thrown, Status.CONFLICT, BusinessFlowException.class, CauseType.DUPRICATE);
    }

    @Test
    void testUpdate() {
        var update = RentalItemResourceDto.of(2, "UPDATE-1", "UPDATE-2");
        var actual = itemResource.update(update);
        assertThatToString(actual).isEqualTo(update);
    }

    @Test
    @Order(NO_SIDE_EFFECT)
    void testUpdateOnNotFound() {
        var update = RentalItemResourceDto.of(9, "UPDATE-1", "UPDATE-2"); // 該当なし
        var thrown = catchThrowable(() -> itemResource.update(update));
        assertGenericErrorInfo(thrown, Status.NOT_FOUND, BusinessFlowException.class, CauseType.NOT_FOUND);
    }

    @Test
    @Order(NO_SIDE_EFFECT)
    void testUpdateOnParameterError() {
        var update = RentalItemResourceDto.of(null, "@@@@@", "1234567890123456"); // parameter error
        var thrown = catchThrowable(() -> itemResource.update(update));
        assertValidationErrorInfo(thrown, 3);
    }

    @Test
    void testDelete() {
        var beforeSize = itemResource.getAll().size();
        itemResource.delete(4);
        var afterSize = itemResource.getAll().size();
        assertThat(afterSize).isEqualTo(beforeSize - 1); // 1件削除されていること
    }

    @Test
    @Order(NO_SIDE_EFFECT)
    void testDeleteOnParameterError() {
        var thrown = catchThrowable(() -> itemResource.delete(-1)); // parameter error
        assertValidationErrorInfo(thrown, 1);
    }

    @Test
    @Order(NO_SIDE_EFFECT)
    void testDeleteOnNotFound() {
        var thrown = catchThrowable(() -> itemResource.delete(999)); // 該当なし
        assertGenericErrorInfo(thrown, Status.NOT_FOUND, BusinessFlowException.class, CauseType.NOT_FOUND);
    }

    @Test
    @Order(NO_SIDE_EFFECT)
    void testExist() {
        var actual = itemResource.exists(1);
        assertThat(actual).isTrue();
        actual = itemResource.exists(999);
        assertThat(actual).isFalse();
    }
}
