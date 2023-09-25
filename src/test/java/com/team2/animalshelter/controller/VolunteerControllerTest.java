package com.team2.animalshelter.controller;

import com.github.javafaker.Faker;
import com.team2.animalshelter.dto.in.VolunteerDtoIn;
import com.team2.animalshelter.dto.out.ShelterDtoOut;
import com.team2.animalshelter.dto.out.VolunteerDtoOut;
import com.team2.animalshelter.exception.VolunteerNotFoundException;
import com.team2.animalshelter.service.VolunteerService;
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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

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
@WebMvcTest(controllers = VolunteerController.class)
class VolunteerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VolunteerService volunteerService;

    private static final Faker faker = new Faker();
    private static final long VOLUNTEER_TELEGRAM_ID = 1L;
    private static final String VOLUNTEER_NAME = "name";
    private static final String VOLUNTEER_FIRST_NAME = "first_name";
    private static final String VOLUNTEER_LAST_NAME = "last_name";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final JSONObject VOLUNTEER_OBJECT = new JSONObject();
    private static final ShelterDtoOut SHELTER_DTO_OUT = new ShelterDtoOut(
            faker.name().toString(),
            faker.address().toString(),
            faker.phoneNumber().toString(),
            faker.address().timeZone(),
            faker.address().streetAddress(),
            faker.company().url()
    );
    public static final VolunteerDtoIn VOLUNTEER_DTO_IN = new VolunteerDtoIn(
            VOLUNTEER_TELEGRAM_ID, VOLUNTEER_NAME, VOLUNTEER_FIRST_NAME, VOLUNTEER_LAST_NAME
    );
    private static final VolunteerDtoOut VOLUNTEER_DTO_OUT = new VolunteerDtoOut(
            VOLUNTEER_TELEGRAM_ID, VOLUNTEER_NAME, VOLUNTEER_FIRST_NAME, VOLUNTEER_LAST_NAME
    );

    @BeforeEach
    void setUp() {
        VOLUNTEER_OBJECT.put("telegramId", VOLUNTEER_TELEGRAM_ID);
        VOLUNTEER_OBJECT.put("username", VOLUNTEER_NAME);
        VOLUNTEER_OBJECT.put("firstname", VOLUNTEER_FIRST_NAME);
        VOLUNTEER_OBJECT.put("lastname", VOLUNTEER_LAST_NAME);
    }

    @Nested
    @Tag("volunteerControllerCrud")
    class CrudTest {

        @Test
        @DisplayName("get [positive]")
        void findById() throws Exception {
            when(volunteerService.findById(any(Long.class))).thenReturn(Optional.of(VOLUNTEER_DTO_OUT));

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/v1/volunteers/" + VOLUNTEER_TELEGRAM_ID)
                            .content(VOLUNTEER_OBJECT.toString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.telegramId").value(VOLUNTEER_TELEGRAM_ID))
                    .andExpect(jsonPath("$.username").value(VOLUNTEER_NAME))
                    .andExpect(jsonPath("$.firstname").value(VOLUNTEER_FIRST_NAME))
                    .andExpect(jsonPath("$.lastname").value(VOLUNTEER_LAST_NAME));
            verify(volunteerService, times(1)).findById(anyLong());
        }

        @Test
        @DisplayName("get [negative]")
        void findVolunteerByIdShouldReturn404() throws Exception {
            when(volunteerService.findById(anyLong())).thenThrow(VolunteerNotFoundException.class);
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/volunteers/" + VOLUNTEER_TELEGRAM_ID))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("get [findAll]")
        void findAll() throws Exception {
            when(volunteerService.findAll()).thenReturn(List.of(VOLUNTEER_DTO_OUT));
            mockMvc.perform(
                            MockMvcRequestBuilders
                                    .get("/api/v1/volunteers/")
                                    .content(VOLUNTEER_OBJECT.toString())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$[0].telegramId").value(VOLUNTEER_TELEGRAM_ID))
                    .andExpect(jsonPath("$[0].username").value(VOLUNTEER_NAME))
                    .andExpect(jsonPath("$[0].firstname").value(VOLUNTEER_FIRST_NAME))
                    .andExpect(jsonPath("$[0].lastname").value(VOLUNTEER_LAST_NAME));
            verify(volunteerService, times(1)).findAll();
        }

        @Test
        @DisplayName("create")
        void create() throws Exception {
            when(volunteerService.create(any(VolunteerDtoIn.class))).thenReturn(VOLUNTEER_DTO_OUT);
            mockMvc.perform(
                            MockMvcRequestBuilders
                                    .post("/api/v1/volunteers")
                                    .content(objectMapper.writeValueAsString(VOLUNTEER_DTO_IN))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isCreated())
                    .andDo(print())
                    .andExpect(jsonPath("$.telegramId").value(VOLUNTEER_TELEGRAM_ID))
                    .andExpect(jsonPath("$.username").value(VOLUNTEER_NAME))
                    .andExpect(jsonPath("$.firstname").value(VOLUNTEER_FIRST_NAME))
                    .andExpect(jsonPath("$.lastname").value(VOLUNTEER_LAST_NAME));
            verify(volunteerService, times(1)).create(any(VolunteerDtoIn.class));
        }

        @Test
        @DisplayName("update")
        void update() throws Exception {
            when(volunteerService.update(anyLong(), any(VolunteerDtoIn.class))).thenReturn(Optional.of(VOLUNTEER_DTO_OUT));
            mockMvc.perform(
                            MockMvcRequestBuilders
                                    .put("/api/v1/volunteers/" + VOLUNTEER_DTO_IN.getTelegramId(), VOLUNTEER_DTO_IN)
                                    .content(objectMapper.writeValueAsString(VOLUNTEER_DTO_IN))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.telegramId").value(VOLUNTEER_TELEGRAM_ID))
                    .andExpect(jsonPath("$.username").value(VOLUNTEER_NAME))
                    .andExpect(jsonPath("$.firstname").value(VOLUNTEER_FIRST_NAME))
                    .andExpect(jsonPath("$.lastname").value(VOLUNTEER_LAST_NAME));
            verify(volunteerService, times(1)).update(anyLong(), any(VolunteerDtoIn.class));
        }

        @Test
        @DisplayName("update [negative]")
        void updateShouldReturn404IfVolunteerIdNotFound() throws Exception {
            when(volunteerService.update(anyLong(), any(VolunteerDtoIn.class)))
                    .thenThrow(VolunteerNotFoundException.class);
            mockMvc.perform(
                            MockMvcRequestBuilders
                                    .put("/api/v1/volunteers/" + VOLUNTEER_DTO_IN.getTelegramId(), VOLUNTEER_DTO_IN)
                                    .content(objectMapper.writeValueAsString(VOLUNTEER_DTO_IN))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("delete [positive]")
        void delete() throws Exception {
            when(volunteerService.delete(any(Long.class))).thenReturn(true);

            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/api/v1/volunteers/" + VOLUNTEER_DTO_IN.getTelegramId())
                            .content(objectMapper.writeValueAsString(VOLUNTEER_DTO_IN))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(204))
                    .andDo(print());
            verify(volunteerService, times(1)).delete(anyLong());
        }

        @Test
        @DisplayName("delete [negative]")
        void deleteShouldReturn404() throws Exception {
            when(volunteerService.delete(any(Long.class))).thenThrow(VolunteerNotFoundException.class);

            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/api/v1/volunteers/" + VOLUNTEER_DTO_IN.getTelegramId())
                    )
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }
    }

}