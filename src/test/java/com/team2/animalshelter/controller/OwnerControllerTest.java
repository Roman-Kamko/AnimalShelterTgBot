package com.team2.animalshelter.controller;

import com.team2.animalshelter.dto.OwnerDto;
import com.team2.animalshelter.exception.OwnerNotFoundException;
import com.team2.animalshelter.service.OwnerService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
@WebMvcTest(controllers = OwnerController.class)
class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerService ownerService;

    private static final long OWNER_TELEGRAM_ID = 1L;
    private static final String OWNER_NAME = "name";
    private static final String OWNER_FIRST_NAME = "first_name";
    private static final String OWNER_LAST_NAME = "last_name";
    private static final String OWNER_PHONE_NUMBER = "+79969598987";
    private static final JSONObject OWNER_OBJECT = new JSONObject();
    public static final OwnerDto OWNER_DTO_OUT = new OwnerDto(OWNER_TELEGRAM_ID, OWNER_NAME, OWNER_FIRST_NAME, OWNER_LAST_NAME, OWNER_PHONE_NUMBER);


    @BeforeEach
    void setUp() {
        OWNER_OBJECT.put("telegramId", OWNER_TELEGRAM_ID);
        OWNER_OBJECT.put("username", OWNER_NAME);
        OWNER_OBJECT.put("firstname", OWNER_FIRST_NAME);
        OWNER_OBJECT.put("lastname", OWNER_LAST_NAME);
        OWNER_OBJECT.put("phoneNumber", OWNER_PHONE_NUMBER);
    }


    @Nested
    @Tag("ownerControllerCrud")
    class CrudTest {
        @Test
        @DisplayName("findAll")
        void findAll() throws Exception {
            when(ownerService.findAll()).thenReturn(List.of(OWNER_DTO_OUT));
            mockMvc.perform(
                            MockMvcRequestBuilders
                                    .get("/api/v1/owners/")
                                    .content(OWNER_OBJECT.toString())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$[0].telegramId").value(OWNER_TELEGRAM_ID))
                    .andExpect(jsonPath("$[0].username").value(OWNER_NAME))
                    .andExpect(jsonPath("$[0].firstname").value(OWNER_FIRST_NAME))
                    .andExpect(jsonPath("$[0].lastname").value(OWNER_LAST_NAME))
                    .andExpect(jsonPath("$[0].phoneNumber").value(OWNER_PHONE_NUMBER));
            verify(ownerService, times(1)).findAll();
        }

        @Test
        @DisplayName("get [positive]")
        void findOwnerById() throws Exception {
            when(ownerService.findById(any(Long.class))).thenReturn(Optional.of(OWNER_DTO_OUT));

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/v1/owners/" + OWNER_TELEGRAM_ID)
                            .content(OWNER_OBJECT.toString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.telegramId").value(OWNER_TELEGRAM_ID))
                    .andExpect(jsonPath("$.username").value(OWNER_NAME))
                    .andExpect(jsonPath("$.firstname").value(OWNER_FIRST_NAME))
                    .andExpect(jsonPath("$.lastname").value(OWNER_LAST_NAME))
                    .andExpect(jsonPath("$.phoneNumber").value(OWNER_PHONE_NUMBER));
            verify(ownerService, times(1)).findById(anyLong());
        }

        @Test
        @DisplayName("get [negative]")
        void findOwnerByIdShouldReturn404() throws Exception {
            when(ownerService.findById(anyLong())).thenThrow(OwnerNotFoundException.class);
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/owners/" + OWNER_TELEGRAM_ID))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("create")
        void create() throws Exception {
            when(ownerService.create(any(Long.class))).thenReturn(Optional.of(OWNER_DTO_OUT).get());
            mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/v1/owners/" + OWNER_TELEGRAM_ID)
                            .content(OWNER_OBJECT.toString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.telegramId").value(OWNER_TELEGRAM_ID))
                    .andExpect(jsonPath("$.username").value(OWNER_NAME))
                    .andExpect(jsonPath("$.firstname").value(OWNER_FIRST_NAME))
                    .andExpect(jsonPath("$.lastname").value(OWNER_LAST_NAME))
                    .andExpect(jsonPath("$.phoneNumber").value(OWNER_PHONE_NUMBER));
            verify(ownerService, times(1)).create(anyLong());
        }

        @Test
        @DisplayName("update positive")
        void update() throws Exception {
            when(ownerService.update(anyLong(), any(OwnerDto.class))).thenReturn(Optional.of(OWNER_DTO_OUT));

            mockMvc.perform(MockMvcRequestBuilders
                            .put("/api/v1/owners/" + OWNER_TELEGRAM_ID, OWNER_DTO_OUT)
                            .content(OWNER_OBJECT.toString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.telegramId").value(OWNER_TELEGRAM_ID))
                    .andExpect(jsonPath("$.username").value(OWNER_NAME))
                    .andExpect(jsonPath("$.firstname").value(OWNER_FIRST_NAME))
                    .andExpect(jsonPath("$.lastname").value(OWNER_LAST_NAME))
                    .andExpect(jsonPath("$.phoneNumber").value(OWNER_PHONE_NUMBER));
            verify(ownerService, times(1)).update(anyLong(), any(OwnerDto.class));
        }

        @Test
        @DisplayName("update [negative]")
        void updateShouldReturn404IfOwnerIdNotFound() throws Exception {
            when(ownerService.update(anyLong(), any(OwnerDto.class)))
                    .thenThrow(OwnerNotFoundException.class);
            mockMvc.perform(
                            MockMvcRequestBuilders
                                    .put("/api/v1/owners/" + OWNER_TELEGRAM_ID, OWNER_DTO_OUT)
                                    .content(OWNER_OBJECT.toString())
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("delete [positive]")
        void delete() throws Exception {
            when(ownerService.delete(any(Long.class))).thenReturn(true);

            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/api/v1/owners/" + OWNER_TELEGRAM_ID)
                            .content(OWNER_OBJECT.toString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(204))
                    .andDo(print());
            verify(ownerService, times(1)).delete(anyLong());
        }

        @Test
        @DisplayName("delete [negative]")
        void deleteShouldReturn404() throws Exception {
            when(ownerService.delete(any(Long.class))).thenThrow(OwnerNotFoundException.class);

            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/api/v1/owners/" + OWNER_TELEGRAM_ID)
                    )
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }
    }

}