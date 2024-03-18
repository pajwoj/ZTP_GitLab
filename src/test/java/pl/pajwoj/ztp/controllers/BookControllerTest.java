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
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void addStatus400() throws Exception {
        mvc.perform(post("/api/books/add")
                        .param("isbn", "")
                        .param("name", "")
                        .param("author", ""))
                .andExpect(status().is(400));
    }

    @Test
    @Order(1)
    public void addStatus200() throws Exception {
        mvc.perform(post("/api/books/add")
                        .param("isbn", "ISBN")
                        .param("name", "NAME")
                        .param("author", "AUTHOR"))
                .andExpect(status().is(200));
    }

    @Test
    public void getStatus400() throws Exception {
        MvcResult res = mvc.perform(get("/api/books/99999"))
                .andExpect(status().is(400))
                .andReturn();

        System.out.println(res.getResponse().getContentAsString());
    }

    @Test
    public void getStatus200() throws Exception {
        MvcResult res = mvc.perform(get("/api/books/1"))
                .andExpect(status().is(200))
                .andReturn();

        System.out.println(res.getResponse().getContentAsString());
    }

    @Test
    public void deleteStatus400() throws Exception {
        mvc.perform(delete("/api/books/delete")
                        .param("isbn", ""))
                .andExpect(status().is(400));
    }

    @Test
    @Order(Ordered.LOWEST_PRECEDENCE)
    public void deleteStatus200() throws Exception {
        mvc.perform(delete("/api/books/delete")
                        .param("isbn", "ISBN"))
                .andExpect(status().is(200));
    }
}