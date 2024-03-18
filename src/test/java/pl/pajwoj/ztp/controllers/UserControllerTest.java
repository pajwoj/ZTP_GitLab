package pl.pajwoj.ztp.controllers;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.Ordered;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.param;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void registerStatus400() throws Exception {
        mvc.perform(post("/api/users/register")
                        .param("email", "")
                        .param("password", ""))
                .andExpect(status().is(400));
    }

    @Test
    @Order(1)
    public void registerStatus200() throws Exception {
        mvc.perform(post("/api/users/register")
                        .param("email", "mock@email.com")
                        .param("password", "Pa$$w0rd"))
                .andExpect(status().is(200));
    }

    @Test
    public void deleteStatus400() throws Exception {
        mvc.perform(delete("/api/users/delete")
                        .param("email", ""))
                .andExpect(status().is(400));
    }

    @Test
    @Order(Ordered.LOWEST_PRECEDENCE)
    public void deleteStatus200() throws Exception {
        mvc.perform(post("/api/users/register")
                .param("email", "mock@email.com")
                .param("password", "Pa$$w0rd"));

        mvc.perform(delete("/api/users/delete")
                        .param("email", "mock@email.com"))
                .andExpect(status().is(200));
    }
}