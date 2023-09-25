package com.team2.animalshelter.controller;

import com.team2.animalshelter.dto.in.ShelterDtoIn;
import com.team2.animalshelter.dto.out.ShelterDtoOut;
import com.team2.animalshelter.service.ShelterService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

import static com.team2.animalshelter.controller.ReportControllerTest.MULTIPART_IMAGES;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
@WebMvcTest(controllers = ShelterController.class)
class ShelterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShelterService shelterService;
    public static final String SHELTER_NAME = "Alga";
    public static final String SHELTER_ADDRESS = "Address";
    public static final String SHELTER_PHONE = "+79969597971";
    public static final String SHELTER_TIME_TABLE = "11:00";
    public static final String SHELTER_DRIVING_DIR = "/dir";
    public static final String SHELTER_DRIVING_URL = "/url";
    public static final byte[] SHELTER_IMAGE_BYTE = {11, 12, 13, 14};
    public static final MockMultipartFile MULTIPART_JSON = new MockMultipartFile("dto",
            "",
            "application/json",
            "{\"name\": \"name\",\"address\": \"address\",\"phoneNumber\": \"+79969597971\",\"timeTable\": \"11:00\"}".getBytes());
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final ShelterDtoIn SHELTER_DTO_IN = new ShelterDtoIn(SHELTER_NAME, SHELTER_ADDRESS, SHELTER_PHONE, SHELTER_TIME_TABLE);

    public static final ShelterDtoOut SHELTER_DTO_OUT = new ShelterDtoOut(
            SHELTER_NAME, SHELTER_ADDRESS, SHELTER_PHONE, SHELTER_TIME_TABLE, SHELTER_DRIVING_DIR, SHELTER_DRIVING_URL);

    @Test
    void getShelter() throws Exception {
        when(shelterService.getShelter()).thenReturn(Optional.of(SHELTER_DTO_OUT));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/shelters")
                        .content(OBJECT_MAPPER.writeValueAsString(SHELTER_DTO_IN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name").value(SHELTER_DTO_OUT.getName()))
                .andExpect(jsonPath("$.address").value(SHELTER_DTO_OUT.getAddress()))
                .andExpect(jsonPath("$.phoneNumber").value(SHELTER_DTO_OUT.getPhoneNumber()))
                .andExpect(jsonPath("$.timeTable").value(SHELTER_DTO_OUT.getTimeTable()));
        verify(shelterService, times(1)).getShelter();
    }

    @Test
    void findDrivingDirections() throws Exception {
        when(shelterService.getImage()).thenReturn(Optional.of(SHELTER_IMAGE_BYTE));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/shelters/map")
                        .content(OBJECT_MAPPER.writeValueAsString(SHELTER_IMAGE_BYTE))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
        verify(shelterService, times(1)).getImage();
    }

    @Test
    void update() throws Exception {
        when(shelterService.update(any(ShelterDtoIn.class), any(MultipartFile.class))).thenReturn(Optional.of(SHELTER_DTO_OUT));
        mockMvc.perform(
                        MockMvcRequestBuilders.multipart(HttpMethod.PUT, "/api/v1/shelters")
                                .file(MULTIPART_JSON)
                                .file(MULTIPART_IMAGES)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}