package com.team2.animalshelter.controller;

import com.team2.animalshelter.dto.OwnerDto;
import com.team2.animalshelter.dto.in.AdaptationDtoIn;
import com.team2.animalshelter.dto.out.AdaptationDtoOut;
import com.team2.animalshelter.dto.out.AnimalDtoOut;
import com.team2.animalshelter.entity.enums.AdaptationStatus;
import com.team2.animalshelter.service.AdaptationService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Optional;

import static com.team2.animalshelter.controller.AnimalControllerTest.ANIMAL_DTO_OUT;
import static com.team2.animalshelter.controller.OwnerControllerTest.OWNER_DTO_OUT;
import static org.mockito.ArgumentMatchers.any;
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
    public static final Boolean ADAPTATION_PROBLEM = false;

    public static final AdaptationDtoIn ADAPTATION_DTO_IN = new AdaptationDtoIn(ADAPTATION_STATUS, ADAPTATION_ANIMAL_ID, ADAPTATION_OWNER_ID);

    public static final AdaptationDtoOut ADAPTATION_DTO_OUT = new AdaptationDtoOut(
            ADAPTATION_ID, START_DATE, END_DATE, ADAPTATION_COMMENT, ADAPTATION_PROBLEM, ADAPTATION_STATUS, ANIMAL_DTO_OUT, OWNER_DTO_OUT
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
        ADAPTATION_JSON.put("problem", ADAPTATION_DTO_OUT.getProblem());
        ADAPTATION_JSON.put("adaptationStatus", ADAPTATION_DTO_OUT.getAdaptationStatus().toString());
        ADAPTATION_JSON.put("animal", ADAPTATION_DTO_OUT.getAnimal());
        ADAPTATION_JSON.put("owner", ADAPTATION_DTO_OUT.getOwner());
    }

    @Nested
    @Tag("adaptationControllerCrud")
    class CrudTest {

        /*@Test
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
                    .andExpect(jsonPath("$.problem").value(ADAPTATION_DTO_OUT.getProblem()))
                    .andExpect(jsonPath("$.adaptationStatus").value(ADAPTATION_DTO_OUT.getAdaptationStatus().toString()))
                    .andExpect(animal("animal", ADAPTATION_DTO_OUT.getAnimal()))
                    .andExpect(owner("owner", ADAPTATION_DTO_OUT.getOwner()));
            verify(adaptationService, times(1)).findById(anyLong());
        }*/

        @Test
        void findAll() {
        }

        @Test
        void findAllByAdaptationStatus() {
        }

        @Test
        void findAllWithProblem() {
        }

        @Test
        void create() {
        }

        @Test
        void update() {
        }

        @Test
        void delete() {
        }
    }

}