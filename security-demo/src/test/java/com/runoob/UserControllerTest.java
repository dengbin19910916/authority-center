package com.runoob;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author DENGBIN
 * @since 2018-3-28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void whenQuerySuccess() throws Exception {
        String result = mockMvc.perform(get("/user")
                .param("username", "dengbin")
                .param("age", "27")
                .param("ageTo", "60")
                .param("xxx", "yyy")
                .param("size", "15")
                .param("page", "3")
                .param("sort", "age,desc")
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void whenGetInfoSuccess() throws Exception {
        String result = mockMvc.perform(get("/user/1")
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("db"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void whenGetInfoFail() throws Exception {
        mockMvc.perform(get("/user/a")
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenCreateSuccess() throws Exception {
        Date birthday = new Date();
        String content = "{\"username\":\"tom\",\"password\":null,\"birthday\":" + birthday.getTime() + "}";
        String result = mockMvc.perform(post("/user")
                .contentType(APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.username").value("tom"))
                .andReturn().getResponse().getContentAsString();
        System.out.println("==> " + result);
    }

    @Test
    public void whenUpdateSuccess() throws Exception {
        Date birthday = new Date();
        String content = "{\"username\":\"db\",\"password\":1231,\"birthday\":" + birthday.getTime() + "}";
        String result = mockMvc.perform(put("/user/1")
                .contentType(APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("db"))
                .andExpect(jsonPath("$.password").value(1231))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("==> " + result);
    }

    @Test
    public void whenDeleteSuccess() throws Exception {
        mockMvc.perform(delete("/user/4")
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }
}
