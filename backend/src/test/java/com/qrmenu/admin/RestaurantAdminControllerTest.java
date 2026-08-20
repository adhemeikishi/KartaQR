package com.qrmenu.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RestaurantAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void adminEndpointsRequireAuthentication() throws Exception {
        mockMvc.perform(get("/api/admin/restaurants"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createsRestaurantWithValidAdminCredentials() throws Exception {
        mockMvc.perform(post("/api/admin/restaurants")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "test-password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Kebab Central\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Kebab Central"))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void rejectsBlankRestaurantName() throws Exception {
        mockMvc.perform(post("/api/admin/restaurants")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "test-password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
}
