package com.team2.animalshelter.controller;

import com.team2.animalshelter.dto.UserDto;
import com.team2.animalshelter.exception.OwnerNotFoundException;
import com.team2.animalshelter.exception.UserNotFoundException;
import com.team2.animalshelter.service.UserService;
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
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private static final long USER_TELEGRAM_ID = 1L;
    private static final String USER_NAME = "name";
    private static final String USER_FIRST_NAME = "first_name";
    private static final String USER_LAST_NAME = "last_name";
    private static final String USER_PHONE_NUMBER = "+79969598987";
    private static final JSONObject USER_OBJECT = new JSONObject();
    private static final UserDto USER_DTO_OUT = new UserDto(USER_TELEGRAM_ID, USER_NAME, USER_FIRST_NAME, USER_LAST_NAME, USER_PHONE_NUMBER);

    @BeforeEach
    void setUp() {
        USER_OBJECT.put("telegramId", USER_TELEGRAM_ID);
        USER_OBJECT.put("username", USER_NAME);
        USER_OBJECT.put("firstname", USER_FIRST_NAME);
        USER_OBJECT.put("lastname", USER_LAST_NAME);
        USER_OBJECT.put("phoneNumber", USER_PHONE_NUMBER);
    }


    @Nested
    @Tag("userControllerCrud")
    class CrudTest {
        @Test
        void findAll() throws Exception {
            when(userService.findAll()).thenReturn(List.of(USER_DTO_OUT));
            mockMvc.perform(
                            MockMvcRequestBuilders
                                    .get("/api/v1/users/")
                                    .content(USER_OBJECT.toString())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$[0].telegramId").value(USER_TELEGRAM_ID))
                    .andExpect(jsonPath("$[0].username").value(USER_NAME))
                    .andExpect(jsonPath("$[0].firstname").value(USER_FIRST_NAME))
                    .andExpect(jsonPath("$[0].lastname").value(USER_LAST_NAME))
                    .andExpect(jsonPath("$[0].phoneNumber").value(USER_PHONE_NUMBER));
        }

        @Test
        @DisplayName("get [positive]")
        void findUserById() throws Exception {
            when(userService.findById(any(Long.class))).thenReturn(Optional.of(USER_DTO_OUT));

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/v1/users/" + USER_TELEGRAM_ID)
                            .content(USER_OBJECT.toString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.telegramId").value(USER_TELEGRAM_ID))
                    .andExpect(jsonPath("$.username").value(USER_NAME))
                    .andExpect(jsonPath("$.firstname").value(USER_FIRST_NAME))
                    .andExpect(jsonPath("$.lastname").value(USER_LAST_NAME))
                    .andExpect(jsonPath("$.phoneNumber").value(USER_PHONE_NUMBER));
        }

        @Test
        @DisplayName("get [negative]")
        void findUserByIdShouldReturn404() throws Exception {
            when(userService.findById(anyLong())).thenThrow(UserNotFoundException.class);
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/" + USER_TELEGRAM_ID))
                    .andExpect(status().isNotFound());
        }

        @Test
        void create() throws Exception {
            when(userService.create(any(UserDto.class))).thenReturn(USER_DTO_OUT);
            mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/v1/users")
                            .content(USER_OBJECT.toString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.telegramId").value(USER_TELEGRAM_ID))
                    .andExpect(jsonPath("$.username").value(USER_NAME))
                    .andExpect(jsonPath("$.firstname").value(USER_FIRST_NAME))
                    .andExpect(jsonPath("$.lastname").value(USER_LAST_NAME))
                    .andExpect(jsonPath("$.phoneNumber").value(USER_PHONE_NUMBER));
        }

        @Test
        void update() throws Exception {
            when(userService.update(any(UserDto.class))).thenReturn(Optional.of(USER_DTO_OUT));

            mockMvc.perform(MockMvcRequestBuilders
                            .put("/api/v1/users/", USER_DTO_OUT)
                            .content(USER_OBJECT.toString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.telegramId").value(USER_TELEGRAM_ID))
                    .andExpect(jsonPath("$.username").value(USER_NAME))
                    .andExpect(jsonPath("$.firstname").value(USER_FIRST_NAME))
                    .andExpect(jsonPath("$.lastname").value(USER_LAST_NAME))
                    .andExpect(jsonPath("$.phoneNumber").value(USER_PHONE_NUMBER));
        }

        @Test
        @DisplayName("update [negative]")
        void updateShouldReturn404IfUserIdNotFound() throws Exception {
            when(userService.update(any(UserDto.class)))
                    .thenThrow(OwnerNotFoundException.class);
            mockMvc.perform(
                            MockMvcRequestBuilders
                                    .put("/api/v1/users/", USER_DTO_OUT)
                                    .content(USER_OBJECT.toString())
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("delete [positive]")
        void delete() throws Exception {
            when(userService.delete(any(Long.class))).thenReturn(true);

            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/api/v1/users/" + USER_TELEGRAM_ID)
                            .content(USER_OBJECT.toString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(204))
                    .andDo(print());
            verify(userService, times(1)).delete(anyLong());
        }

        @Test
        @DisplayName("delete [negative]")
        void deleteShouldReturn404() throws Exception {
            when(userService.delete(any(Long.class))).thenThrow(UserNotFoundException.class);

            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/api/v1/users/" + USER_TELEGRAM_ID)
                    )
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }

    }

}