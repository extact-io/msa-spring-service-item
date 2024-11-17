package io.extact.msa.spring.item.web;


import static org.hamcrest.CoreMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import io.extact.msa.spring.item.application.RentalItemApplicationService;
import io.extact.msa.spring.item.domain.model.ItemId;
import io.extact.msa.spring.item.domain.model.RentalItem;


/**
 * Controllerの単体テストクラス。
 * 以下の機能が有効になっている。<br>
 * ・ControllerAdvice
 * ・Spring Security
 * ・Method Validation
 */
@WebMvcTest(RentalItemController.class)
@Import(WebConfig.class)
class RentalItemControllerTest {

    private static final RentalItem item1 = RentalItem.reconstruct(1, "A0001", "レンタル品1号");
    private static final RentalItem item2 = RentalItem.reconstruct(2, "A0002", "レンタル品2号");
    private static final RentalItem item3 = RentalItem.reconstruct(3, "A0003", "レンタル品3号");
    private static final RentalItem item4 = RentalItem.reconstruct(4, "A0004", "レンタル品4号");

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RentalItemApplicationService itemService;

    @Test
    @WithMockUser
    void testGetAll() throws Exception {

        // given
        when(itemService.getAll())
                .thenReturn(List.of(item1, item2, item3, item4));
        // when
        mockMvc.perform(get("/items")
                .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].serialNo").value("A0001"))
                .andExpect(jsonPath("$[0].itemName").value("レンタル品1号")); // 2件目以降の確認は省略
    }

    @Test
    @WithMockUser
    void testGetAllReturnEmpty() throws Exception {

        // given
        when(itemService.getAll())
                .thenReturn(List.of());
        // when
        mockMvc.perform(get("/items")
                .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testGetAllOnAuthenticationError() throws Exception {

        // given
        // @WithMockUserなし

        // when
        mockMvc.perform(get("/items")
                .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isUnauthorized());

        // then
        verify(itemService, never()).getAll();
    }

    @Test
    @WithMockUser
    void testGetOne() throws Exception {

        // given
        when(itemService.getById(new ItemId(2)))
                .thenReturn(Optional.of(item2));
        // when
        mockMvc.perform(get("/items/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.serialNo").value("A0002"))
                .andExpect(jsonPath("$.itemName").value("レンタル品2号"));
    }

    @Test
    @WithMockUser
    void testGetOneOnNotFound() throws Exception {

        // given
        when(itemService.getById(new ItemId(9)))
                .thenReturn(Optional.empty());
        // when
        mockMvc.perform(get("/items/{id}", 9)
                .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        // then
        verify(itemService, atLeastOnce()).getById(new ItemId(9));
    }

    @Test
    void testGetOneOnAuthenticationError() throws Exception {

        // given
        // @WithMockUserなし

        // when
        mockMvc.perform(get("/items/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isUnauthorized());

        // then
        verify(itemService, never()).getById(any());
    }

    @Test
    @WithMockUser
    void testGetOneOnParameterError() throws Exception {

        // given
        int errorParm = -1;

        // when
        mockMvc.perform(get("/items/{id}", errorParm)
                .contentType(MediaType.APPLICATION_JSON))
                // then
                .andDo(result -> result.getResponse().setCharacterEncoding("UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("パラメーターエラーが発生しました")));

        // then
        verify(itemService, never()).getById(any());
    }
}