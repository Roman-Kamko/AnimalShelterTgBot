package com.team2.animalshelter.controller;

import com.team2.animalshelter.dto.in.ReportDtoIn;
import com.team2.animalshelter.dto.out.ReportDtoOut;
import com.team2.animalshelter.exception.ReportNotFoundException;
import com.team2.animalshelter.exception.VolunteerNotFoundException;
import com.team2.animalshelter.service.ReportService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.team2.animalshelter.controller.AdaptationControllerTest.ADAPTATION_DTO_OUT;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
@WebMvcTest(controllers = ReportController.class)
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    public static final String REPORT_MESSAGE = "message";
    public static final String REPORT_PHOTO_URL = "/url/photo";
    public static final LocalDate DATE = LocalDate.of(2022, 2, 15);
    public static final Long adaptationId = 1L;
    public static final Long REPORT_ID = 2L;
    public static final byte[] REPORT_BYTE_ARR = {20, 50, 60, 70};
    public static final ReportDtoIn REPORT_DTO_IN = new ReportDtoIn(REPORT_MESSAGE, adaptationId);

    public static final ReportDtoOut REPORT_DTO_OUT = new ReportDtoOut(REPORT_ID, REPORT_MESSAGE, REPORT_PHOTO_URL, DATE, ADAPTATION_DTO_OUT);
    public static final JSONObject REPORT_JSON = new JSONObject();
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final MockMultipartFile MULTIPART_IMAGES = new MockMultipartFile("file",
            "testAddress.jpg",
            MediaType.IMAGE_JPEG.getType(),
            "testAddress.jpg" .getBytes());

    public static final MockMultipartFile MULTIPART_JSON = new MockMultipartFile("dto",
            "",
            "application/json",
            "{\"reportMessage\": \"somevalue\",\"adaptationId\": \"2\"}" .getBytes());

    @BeforeEach
    void setUp() {
        REPORT_JSON.put("id", REPORT_DTO_OUT.getId());
        REPORT_JSON.put("reportMessage", REPORT_DTO_OUT.getReportMessage());
        REPORT_JSON.put("photoUrl", REPORT_DTO_OUT.getPhotoUrl());
        REPORT_JSON.put("date", REPORT_DTO_OUT.getDate());
        REPORT_JSON.put("adaptation", REPORT_DTO_OUT.getAdaptation());
    }

    @Nested
    @Tag("reportControllerCrud")
    class CrudTest {
        @Test
        @DisplayName("get [findAll]")
        void findAll() throws Exception {
            when(reportService.findAll()).thenReturn(List.of(REPORT_DTO_OUT));
            mockMvc.perform(
                            MockMvcRequestBuilders
                                    .get("/api/v1/reports")
                                    .content(OBJECT_MAPPER.writeValueAsString(REPORT_DTO_IN))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$[0].id").value(REPORT_DTO_OUT.getId()))
                    .andExpect(jsonPath("$[0].reportMessage").value(REPORT_DTO_OUT.getReportMessage()))
                    .andExpect(jsonPath("$[0].photoUrl").value(REPORT_DTO_OUT.getPhotoUrl()))
                    .andExpect(jsonPath("$[0].date").value(REPORT_DTO_OUT.getDate().toString()))
                    .andExpect(jsonPath("$[0].adaptation").isNotEmpty());
            verify(reportService, times(1)).findAll();
        }

        @Test
        @DisplayName("get [positive]")
        void findById() throws Exception {
            when(reportService.findById(anyLong())).thenReturn(Optional.of(REPORT_DTO_OUT));
            mockMvc.perform(
                            MockMvcRequestBuilders
                                    .get("/api/v1/reports/" + REPORT_DTO_OUT.getId())
                                    .content(OBJECT_MAPPER.writeValueAsString(REPORT_DTO_IN))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.id").value(REPORT_DTO_OUT.getId()))
                    .andExpect(jsonPath("$.reportMessage").value(REPORT_DTO_OUT.getReportMessage()))
                    .andExpect(jsonPath("$.photoUrl").value(REPORT_DTO_OUT.getPhotoUrl()))
                    .andExpect(jsonPath("$.date").value(REPORT_DTO_OUT.getDate().toString()))
                    .andExpect(jsonPath("$.adaptation").isNotEmpty());
            verify(reportService, times(1)).findById(anyLong());
        }

        @Test
        @DisplayName("get [negative]")
        void findById404Error() throws Exception {
            when(reportService.findById(anyLong())).thenThrow(ReportNotFoundException.class);
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/reports/" + REPORT_DTO_OUT.getId()))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("get [positive]")
        void findDrivingDirections() throws Exception {
            when(reportService.findById(anyLong())).thenReturn(Optional.of(REPORT_DTO_OUT));
            when(reportService.getImage(anyLong())).thenReturn(Optional.of(REPORT_BYTE_ARR));
            mockMvc.perform(
                            MockMvcRequestBuilders
                                    .get("/api/v1/reports/" + REPORT_DTO_OUT.getId() + "/photo")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andDo(print());
            verify(reportService, times(1)).getImage(anyLong());
        }

        @Test
        @DisplayName("get [negative]")
        void findDrivingDirectionsId404Error() throws Exception {
            when(reportService.getImage(anyLong())).thenThrow(ReportNotFoundException.class);
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/reports/" + REPORT_DTO_OUT.getId() + "/photo"))
                    .andExpect(status().isNotFound());
        }

        @Test
        void create() throws Exception {
            mockMvc.perform(
                            MockMvcRequestBuilders.multipart(HttpMethod.POST, "/api/v1/reports")
                                    .file(MULTIPART_JSON)
                                    .file(MULTIPART_IMAGES)
                    )
                    .andExpect(status().isCreated())
                    .andDo(print());
        }

        /*@Test
        void update() throws Exception {
            when(reportService.findById(anyLong())).thenReturn(Optional.of(REPORT_DTO_OUT));
            mockMvc.perform(
                            MockMvcRequestBuilders.multipart(HttpMethod.PUT, "/api/v1/reports/" + 1L)
                                    .file(MULTIPART_JSON)
                                    .file(MULTIPART_IMAGES)
                    )
                    .andExpect(status().isOk())
                    .andDo(print());
        }*/

        @Test
        void delete() throws Exception {
            when(reportService.delete(anyLong())).thenReturn(true);

            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/api/v1/reports/{id}", REPORT_DTO_OUT.getId())
                            .content(OBJECT_MAPPER.writeValueAsString(REPORT_DTO_IN))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(204))
                    .andDo(print());
            verify(reportService, times(1)).delete(anyLong());
        }

        @Test
        @DisplayName("delete [negative]")
        void deleteShouldReturn404() throws Exception {
            when(reportService.delete(any(Long.class))).thenThrow(ReportNotFoundException.class);

            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/api/v1/reports/" + anyLong())
                    )
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }
    }

}