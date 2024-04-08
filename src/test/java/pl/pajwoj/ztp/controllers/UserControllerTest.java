package pl.pajwoj.ztp.controllers;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.Ordered;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Test
    public void loginStatus400() throws Exception {
        mvc.perform(post("/api/users/login")
                .param("email", "")
                .param("password", ""))
                .andExpect(status().is(400));
    }

    @Test
    @Order(2)
    public void loginStatus401() throws Exception {
        mvc.perform(post("/api/users/login")
                .param("email", "mock@email.com")
                .param("password", ""))
                .andExpect(status().is(401));
    }

    @Test
    @Order(3)
    public void loginStatus200() throws Exception {
        mvc.perform(post("/api/users/register")
                .param("email", "mock@email.com")
                .param("password", "Pa$$w0rd"));

        mvc.perform(post("/api/users/login")
                .param("email", "mock@email.com")
                .param("password", "Pa$$w0rd"))
                .andExpect(status().is(200));
    }

    @Test
    @Order(5)
    public void login2Status200() throws Exception {
        mvc.perform(post("/api/users/register")
                .param("email", "mock@email.com")
                .param("password", "Pa$$w0rd"));

        mvc.perform(post("/api/users/login")
                        .param("email", "mock@email.com")
                        .param("password", "Pa$$w0rd"))
                .andExpect(status().is(200));
    }

    @Test
    @Order(4)
    public void logoutGETStatus200() throws Exception {
        mvc.perform(post("/api/users/login")
                .param("email", "mock@email.com")
                .param("password", "Pa$$w0rd"));

        mvc.perform(get("/api/users/logout"))
                .andExpect(status().is(200));
    }

    @Test
    public void logoutGETStatus400() throws Exception {
        mvc.perform(get("/api/users/logout"))
                .andExpect(status().is(400));
    }

    @Test
    @Order(6)
    public void logoutPOSTStatus200() throws Exception {
        mvc.perform(post("/api/users/login")
                .param("email", "mock@email.com")
                .param("password", "Pa$$w0rd"));

        mvc.perform(post("/api/users/logout"))
                .andExpect(status().is(200));
    }

    @Test
    public void logoutPOSTStatus400() throws Exception {
        mvc.perform(post("/api/users/logout"))
                .andExpect(status().is(400));
    }
}