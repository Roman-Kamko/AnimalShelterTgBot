package com.team2.animalshelter.controller;

import com.team2.animalshelter.dto.OwnerDto;
import com.team2.animalshelter.dto.in.AdaptationDtoIn;
import com.team2.animalshelter.dto.out.AdaptationDtoOut;
import com.team2.animalshelter.dto.out.AnimalDtoOut;
import com.team2.animalshelter.entity.enums.AdaptationStatus;
import com.team2.animalshelter.exception.AdaptationNotFoundException;
import com.team2.animalshelter.service.AdaptationService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.team2.animalshelter.controller.AnimalControllerTest.ANIMAL_DTO_OUT;
import static com.team2.animalshelter.controller.OwnerControllerTest.OWNER_DTO_OUT;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
@WebMvcTest(controllers = AdaptationController.class)
class AdaptationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdaptationService adaptationService;

    public static final JSONObject ADAPTATION_JSON = new JSONObject();

    public static final AdaptationStatus ADAPTATION_STATUS = AdaptationStatus.IN_PROGRESS;

    public static final Long ADAPTATION_ANIMAL_ID = 1L;
    public static final Long ADAPTATION_OWNER_ID = 2L;
    public static final Long ADAPTATION_ID = 3L;

    public static final LocalDate START_DATE = LocalDate.of(2023, 9, 15);
    public static final LocalDate END_DATE = LocalDate.of(2023, 9, 30);

    public static final String ADAPTATION_COMMENT = "comment";
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static final AdaptationDtoIn ADAPTATION_DTO_IN = new AdaptationDtoIn(ADAPTATION_STATUS, ADAPTATION_ANIMAL_ID, ADAPTATION_OWNER_ID);

    public static final AdaptationDtoOut ADAPTATION_DTO_OUT = new AdaptationDtoOut(
            ADAPTATION_ID, START_DATE, END_DATE, ADAPTATION_COMMENT, ADAPTATION_STATUS, ANIMAL_DTO_OUT, OWNER_DTO_OUT
    );

    private static ResultMatcher animal(final String prefix, final AnimalDtoOut animalDtoOut) {
        return ResultMatcher.matchAll(
                (jsonPath(prefix + ".id").value(animalDtoOut.getId())),
                (jsonPath(prefix + ".name").value(animalDtoOut.getName())),
                (jsonPath(prefix + ".age").value(animalDtoOut.getAge())),
                (jsonPath(prefix + ".breed").value(animalDtoOut.getBreed())),
                (jsonPath(prefix + ".healthy").value(animalDtoOut.getHealthy())),
                (jsonPath(prefix + ".animalType").value(animalDtoOut.getAnimalType().toString())));
    }

    private static ResultMatcher owner(final String prefix, final OwnerDto ownerDto) {
        return ResultMatcher.matchAll(
                (jsonPath(prefix + ".telegramId").value(ownerDto.getTelegramId())),
                (jsonPath(prefix + ".username").value(ownerDto.getUsername())),
                (jsonPath(prefix + ".firstname").value(ownerDto.getFirstname())),
                (jsonPath(prefix + ".lastname").value(ownerDto.getLastname())),
                (jsonPath(prefix + ".phoneNumber").value(ownerDto.getPhoneNumber())));
    }

    @BeforeEach
    void setUp() {
        ADAPTATION_JSON.put("id", ADAPTATION_DTO_OUT.getId());
        ADAPTATION_JSON.put("startDate", ADAPTATION_DTO_OUT.getStartDate().toString());
        ADAPTATION_JSON.put("endDate", ADAPTATION_DTO_OUT.getEndDate().toString());
        ADAPTATION_JSON.put("comment", ADAPTATION_DTO_OUT.getComment());
        ADAPTATION_JSON.put("adaptationStatus", ADAPTATION_DTO_OUT.getAdaptationStatus().toString());
        ADAPTATION_JSON.put("animal", ADAPTATION_DTO_OUT.getAnimal());
        ADAPTATION_JSON.put("owner", ADAPTATION_DTO_OUT.getOwner());
    }

    @Nested
    @Tag("adaptationControllerCrud")
    class CrudTest {

        @Test
        @DisplayName("findById")
        void findById() throws Exception {
            when(adaptationService.findById(anyLong())).thenReturn(Optional.of(ADAPTATION_DTO_OUT));

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/v1/adaptations/" + ANIMAL_DTO_OUT.getId())
                            .content(ADAPTATION_JSON.toJSONString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.id").value(ADAPTATION_DTO_OUT.getId()))
                    .andExpect(jsonPath("$.startDate").value(ADAPTATION_DTO_OUT.getStartDate().toString()))
                    .andExpect(jsonPath("$.endDate").value(ADAPTATION_DTO_OUT.getEndDate().toString()))
                    .andExpect(jsonPath("$.comment").value(ADAPTATION_DTO_OUT.getComment()))
                    .andExpect(jsonPath("$.adaptationStatus").value(ADAPTATION_DTO_OUT.getAdaptationStatus().toString()))
                    .andExpect(animal("animal", ADAPTATION_DTO_OUT.getAnimal()))
                    .andExpect(owner("owner", ADAPTATION_DTO_OUT.getOwner()));
            verify(adaptationService, times(1)).findById(anyLong());
        }

        @Test
        @DisplayName("find [negative]")
        void find404NotFound() throws Exception {
            when(adaptationService.findById(anyLong())).thenThrow(AdaptationNotFoundException.class);

            mockMvc.perform(MockMvcRequestBuilders
                            .put("/api/v1/adaptations/{id}", ADAPTATION_ID)
                            .content(OBJECT_MAPPER.writeValueAsString(ADAPTATION_DTO_IN))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }

        @Test
        @DisplayName("findAll")
        void findAll() throws Exception {
            when(adaptationService.findAll()).thenReturn(List.of(ADAPTATION_DTO_OUT));
            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/v1/adaptations")
                            .content(ADAPTATION_JSON.toJSONString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$[0].id").value(ADAPTATION_DTO_OUT.getId()))
                    .andExpect(jsonPath("$[0].startDate").value(ADAPTATION_DTO_OUT.getStartDate().toString()))
                    .andExpect(jsonPath("$[0].endDate").value(ADAPTATION_DTO_OUT.getEndDate().toString()))
                    .andExpect(jsonPath("$[0].comment").value(ADAPTATION_DTO_OUT.getComment()))
                    .andExpect(jsonPath("$[0].adaptationStatus").value(ADAPTATION_DTO_OUT.getAdaptationStatus().toString()))
                    .andExpect(animal("[0].animal", ADAPTATION_DTO_OUT.getAnimal()))
                    .andExpect(owner("[0].owner", ADAPTATION_DTO_OUT.getOwner()));
            verify(adaptationService, times(1)).findAll();
        }

        @Test
        void findAllByAdaptationStatus() throws Exception {
            when(adaptationService.findAllByAdaptationStatus(any(AdaptationStatus.class))).thenReturn(List.of(ADAPTATION_DTO_OUT));
            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/v1/adaptations/{status}/status", ADAPTATION_STATUS)
                            .content(ADAPTATION_JSON.toJSONString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$[0].id").value(ADAPTATION_DTO_OUT.getId()))
                    .andExpect(jsonPath("$[0].startDate").value(ADAPTATION_DTO_OUT.getStartDate().toString()))
                    .andExpect(jsonPath("$[0].endDate").value(ADAPTATION_DTO_OUT.getEndDate().toString()))
                    .andExpect(jsonPath("$[0].comment").value(ADAPTATION_DTO_OUT.getComment()))
                    .andExpect(jsonPath("$[0].adaptationStatus").value(ADAPTATION_DTO_OUT.getAdaptationStatus().toString()))
                    .andExpect(animal("[0].animal", ADAPTATION_DTO_OUT.getAnimal()))
                    .andExpect(owner("[0].owner", ADAPTATION_DTO_OUT.getOwner()));
            verify(adaptationService, times(1)).findAllByAdaptationStatus(any(AdaptationStatus.class));
        }

        @Test
        @DisplayName("create")
        void create() throws Exception {
            when(adaptationService.create(any(AdaptationDtoIn.class))).thenReturn(ADAPTATION_DTO_OUT);

            mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/v1/adaptations")
                            .content(OBJECT_MAPPER.writeValueAsString(ADAPTATION_DTO_IN))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isCreated())
                    .andDo(print())
                    .andExpect(jsonPath("$.id").value(ADAPTATION_DTO_OUT.getId()))
                    .andExpect(jsonPath("$.startDate").value(ADAPTATION_DTO_OUT.getStartDate().toString()))
                    .andExpect(jsonPath("$.endDate").value(ADAPTATION_DTO_OUT.getEndDate().toString()))
                    .andExpect(jsonPath("$.comment").value(ADAPTATION_DTO_OUT.getComment()))
                    .andExpect(jsonPath("$.adaptationStatus").value(ADAPTATION_DTO_OUT.getAdaptationStatus().toString()))
                    .andExpect(animal("animal", ADAPTATION_DTO_OUT.getAnimal()))
                    .andExpect(owner("owner", ADAPTATION_DTO_OUT.getOwner()));
            verify(adaptationService, times(1)).create(any(AdaptationDtoIn.class));
        }

        @Test
        @DisplayName("update")
        void update() throws Exception {
            when(adaptationService.update(anyLong(), any(AdaptationDtoIn.class))).thenReturn(Optional.of(ADAPTATION_DTO_OUT));

            mockMvc.perform(MockMvcRequestBuilders
                            .put("/api/v1/adaptations/{id}", ADAPTATION_ID)
                            .content(OBJECT_MAPPER.writeValueAsString(ADAPTATION_DTO_IN))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.id").value(ADAPTATION_DTO_OUT.getId()))
                    .andExpect(jsonPath("$.startDate").value(ADAPTATION_DTO_OUT.getStartDate().toString()))
                    .andExpect(jsonPath("$.endDate").value(ADAPTATION_DTO_OUT.getEndDate().toString()))
                    .andExpect(jsonPath("$.comment").value(ADAPTATION_DTO_OUT.getComment()))
                    .andExpect(jsonPath("$.adaptationStatus").value(ADAPTATION_DTO_OUT.getAdaptationStatus().toString()))
                    .andExpect(animal("animal", ADAPTATION_DTO_OUT.getAnimal()))
                    .andExpect(owner("owner", ADAPTATION_DTO_OUT.getOwner()));
            verify(adaptationService, times(1)).update(anyLong(), any(AdaptationDtoIn.class));
        }

        @Test
        @DisplayName("update [negative]")
        void update404NotFound() throws Exception {
            when(adaptationService.update(anyLong(), any(AdaptationDtoIn.class))).thenThrow(AdaptationNotFoundException.class);

            mockMvc.perform(MockMvcRequestBuilders
                            .put("/api/v1/adaptations/{id}", ADAPTATION_ID)
                            .content(OBJECT_MAPPER.writeValueAsString(ADAPTATION_DTO_IN))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }

        @Test
        @DisplayName("delete [positive]")
        void delete() throws Exception {
            when(adaptationService.delete(any(Long.class))).thenReturn(true);

            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/api/v1/adaptations/" + ADAPTATION_ID)
                            .content(OBJECT_MAPPER.writeValueAsString(ADAPTATION_DTO_IN))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(204))
                    .andDo(print());
            verify(adaptationService, times(1)).delete(anyLong());
        }

        @Test
        @DisplayName("delete [negative]")
        void deleteShouldReturn404() throws Exception {
            when(adaptationService.delete(any(Long.class))).thenThrow(AdaptationNotFoundException.class);

            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/api/v1/adaptations/" + ADAPTATION_ID)
                    )
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }
    }

}