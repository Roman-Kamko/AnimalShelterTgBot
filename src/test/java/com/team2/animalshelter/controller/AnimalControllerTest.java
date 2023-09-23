package com.team2.animalshelter.controller;

import com.team2.animalshelter.dto.in.AnimalDtoIn;
import com.team2.animalshelter.dto.out.AnimalDtoOut;
import com.team2.animalshelter.entity.enums.AnimalType;
import com.team2.animalshelter.exception.AnimalNotFoundException;
import com.team2.animalshelter.service.AnimalService;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
@WebMvcTest(controllers = AnimalController.class)
class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimalService animalService;


    public static final Long ANIMAL_ID = 1L;
    public static final String ANIMAL_NAME = "Bars";
    public static final Integer ANIMAL_AGE = 2;
    public static final String ANIMAL_BREED = "Sphinx";
    public static final String ANIMAL_IS_HEAlTLY = "false";
    public static final AnimalType ANIMAL_TYPE = AnimalType.CAT;
    public static final Long SHELTER_ID = 1L;
    private static final JSONObject ANIMAL_OBJECT = new JSONObject();

    public static final AnimalDtoIn ANIMAL_DTO_IN = new AnimalDtoIn(
            ANIMAL_NAME, ANIMAL_AGE, ANIMAL_BREED, ANIMAL_IS_HEAlTLY, ANIMAL_TYPE
    );

    public static final AnimalDtoOut ANIMAL_DTO_OUT = new AnimalDtoOut(
            ANIMAL_ID, ANIMAL_NAME, ANIMAL_AGE, ANIMAL_BREED, Boolean.getBoolean(ANIMAL_IS_HEAlTLY), ANIMAL_TYPE
    );

    @BeforeEach
    void setUp() {
        ANIMAL_OBJECT.put("id", ANIMAL_ID);
        ANIMAL_OBJECT.put("name", ANIMAL_NAME);
        ANIMAL_OBJECT.put("age", ANIMAL_AGE);
        ANIMAL_OBJECT.put("breed", ANIMAL_BREED);
        ANIMAL_OBJECT.put("healthy", ANIMAL_IS_HEAlTLY);
        ANIMAL_OBJECT.put("animalType", ANIMAL_TYPE);
    }


    @Nested
    @Tag("animalControllerCrud")
    class CrudTest {
        @Test
        @DisplayName("get [find]")
        void findById() throws Exception {
            when(animalService.findById(anyLong())).thenReturn(Optional.of(ANIMAL_DTO_OUT));

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/v1/animals/" + ANIMAL_ID)
                            .content(ANIMAL_OBJECT.toString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.id").value(ANIMAL_ID))
                    .andExpect(jsonPath("$.name").value(ANIMAL_NAME))
                    .andExpect(jsonPath("$.age").value(ANIMAL_AGE))
                    .andExpect(jsonPath("$.breed").value(ANIMAL_BREED))
                    .andExpect(jsonPath("$.healthy").value(ANIMAL_IS_HEAlTLY))
                    .andExpect(jsonPath("$.animalType").value(ANIMAL_TYPE.toString()));
            verify(animalService, times(1)).findById(anyLong());
        }

        @Test
        @DisplayName("get [negative]")
        void findVolunteerByIdShouldReturn404() throws Exception {
            when(animalService.findById(anyLong())).thenThrow(AnimalNotFoundException.class);
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/animals/" + ANIMAL_ID))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("get [findAllWithoutOwner]")
        void findAllWithoutOwner() throws Exception {
            when(animalService.findAllWithoutOwner(any(AnimalType.class))).thenReturn(List.of(ANIMAL_DTO_OUT));

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/v1/animals/" + ANIMAL_TYPE + "/non-adopted")
                            .content(ANIMAL_OBJECT.toString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$[0].id").value(ANIMAL_ID))
                    .andExpect(jsonPath("$[0].name").value(ANIMAL_NAME))
                    .andExpect(jsonPath("$[0].age").value(ANIMAL_AGE))
                    .andExpect(jsonPath("$[0].breed").value(ANIMAL_BREED))
                    .andExpect(jsonPath("$[0].healthy").value(ANIMAL_IS_HEAlTLY))
                    .andExpect(jsonPath("$[0].animalType").value(ANIMAL_TYPE.toString()));
            verify(animalService, times(1)).findAllWithoutOwner(any(AnimalType.class));
        }

        @Test
        @DisplayName("get [findAllAdopted]")
        void findAllAdopted() throws Exception {
            when(animalService.findAllAdopted(any(AnimalType.class))).thenReturn(List.of(ANIMAL_DTO_OUT));

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/v1/animals/" + ANIMAL_TYPE + "/adopted")
                            .content(ANIMAL_OBJECT.toString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$[0].id").value(ANIMAL_ID))
                    .andExpect(jsonPath("$[0].name").value(ANIMAL_NAME))
                    .andExpect(jsonPath("$[0].age").value(ANIMAL_AGE))
                    .andExpect(jsonPath("$[0].breed").value(ANIMAL_BREED))
                    .andExpect(jsonPath("$[0].healthy").value(ANIMAL_IS_HEAlTLY))
                    .andExpect(jsonPath("$[0].animalType").value(ANIMAL_TYPE.toString()));
            verify(animalService, times(1)).findAllAdopted(any(AnimalType.class));
        }

        @Test
        void create() throws Exception {
            when(animalService.create(any(AnimalDtoIn.class))).thenReturn(ANIMAL_DTO_OUT);

            mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/v1/animals")
                            .content(ANIMAL_OBJECT.toJSONString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isCreated())
                    .andDo(print())
                    .andExpect(jsonPath("$.id").value(ANIMAL_ID))
                    .andExpect(jsonPath("$.name").value(ANIMAL_NAME))
                    .andExpect(jsonPath("$.age").value(ANIMAL_AGE))
                    .andExpect(jsonPath("$.breed").value(ANIMAL_BREED))
                    .andExpect(jsonPath("$.healthy").value(ANIMAL_IS_HEAlTLY))
                    .andExpect(jsonPath("$.animalType").value(ANIMAL_TYPE.toString()));
            verify(animalService, times(1)).create(any(AnimalDtoIn.class));
        }

        @Test
        void update() throws Exception {
            when(animalService.update(anyLong(), any(AnimalDtoIn.class))).thenReturn(Optional.of(ANIMAL_DTO_OUT));

            mockMvc.perform(MockMvcRequestBuilders
                            .put("/api/v1/animals/" + SHELTER_ID, ANIMAL_DTO_IN)
                            .content(ANIMAL_OBJECT.toJSONString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.id").value(ANIMAL_ID))
                    .andExpect(jsonPath("$.name").value(ANIMAL_NAME))
                    .andExpect(jsonPath("$.age").value(ANIMAL_AGE))
                    .andExpect(jsonPath("$.breed").value(ANIMAL_BREED))
                    .andExpect(jsonPath("$.healthy").value(ANIMAL_IS_HEAlTLY))
                    .andExpect(jsonPath("$.animalType").value(ANIMAL_TYPE.toString()));
            verify(animalService, times(1)).update(anyLong(), any(AnimalDtoIn.class));
        }

        @Test
        @DisplayName("delete [positive]")
        void delete() throws Exception {
            when(animalService.delete(any(Long.class))).thenReturn(true);

            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/api/v1/animals/" + SHELTER_ID)
                            .content(ANIMAL_OBJECT.toJSONString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(204))
                    .andDo(print());
            verify(animalService, times(1)).delete(anyLong());
        }

        @Test
        @DisplayName("delete [negative]")
        void deleteShouldReturn404() throws Exception {
            when(animalService.delete(any(Long.class))).thenThrow(AnimalNotFoundException.class);

            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/api/v1/animals/" + SHELTER_ID)
                    )
                    .andExpect(status().is(404))
                    .andDo(print());
            verify(animalService, times(1)).delete(anyLong());
        }
    }

}