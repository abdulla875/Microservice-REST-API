package com.ah.fyp.contronller;

import com.ah.fyp.service.impl.CrimeDataServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebAppConfiguration
@SpringBootTest
public class PoliceDataControllerTest {
    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private CrimeDataServiceImpl crimeDataService;

    private MockMvc mvc;


    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType()
            ,MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Test
    public void getCrimeDataShouldReturnOkStatusWhenCorrectUrlProvidedTest() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        this.mvc.perform(get("/get-crime-data-by-location"))
                .andExpect(status()
                        .isOk());
    }

    @Test
    public void getCrimeDataShouldReturnOkStatusWhenCorrectUrlProvidedAndSavedDBTest() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        this.mvc.perform(get("/get-crime-data-fromDb"))
                .andExpect(status()
                        .isOk());
    }

    @Test
    public void getCrimeDataShouldReturnNotFoundStatusWhenInCorrectUrlProvidedTest() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        this.mvc.perform(get("/get-crime-data-incorrect"))
                .andExpect(status()
                        .isNotFound());
    }

}
