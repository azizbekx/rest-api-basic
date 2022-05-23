package com.epam.esm;

import com.epam.esm.config.SpringConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class RestApiTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception{
        this.mockMvc =
                MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .build();
    }

    @Test
    public void servletContextTest(){
        ServletContext servletContext = webApplicationContext
                .getServletContext();
        Assertions.assertNotNull(servletContext);
        Assertions.assertTrue(servletContext instanceof MockServletContext);
    }
}
