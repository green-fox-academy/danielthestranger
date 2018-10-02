package com.greenfoxacademy.restbackend.controller;

import com.greenfoxacademy.restbackend.service.ArrayActionService;
import com.greenfoxacademy.restbackend.service.DoUntilService;
import com.greenfoxacademy.restbackend.service.LogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(MainRestController.class)
public class MainRestControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    private String jsonErrorPath ="$.error";
    private String doublingEndPoint ="/doubling";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArrayActionService arrayActionService;
    @MockBean
    private DoUntilService doUntilService;
    @MockBean
    private LogService logService;

    @Test
    public void doublingReturnsError_on_noParam() throws Exception {
        String endpoint = doublingEndPoint;
        String[] urlTemplates = {endpoint, endpoint+"?", endpoint+"?input="};

        String errorMsg = "Please provide an input!";

        for (String urlTemplate : urlTemplates) {
            mockMvc.perform(get(urlTemplate))
                    .andExpect(status().isAccepted())
                    .andExpect(content().contentType(contentType))
                    .andExpect(jsonPath(jsonErrorPath, is(errorMsg)))
                    .andDo(print());
        }
    }

    @Test
    public void doublingReturnsJson_on_validInput() throws Exception {
        String endpoint = doublingEndPoint;
        String[] urlTemplates = {endpoint+"?input=15", endpoint+"?input=0"};
        Double[] receiveds = {15., 0.};
        Double[] results = {30., 0.};

        for (int i = 0; i < urlTemplates.length; i++) {
            mockMvc.perform(get(urlTemplates[i]))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(contentType))
                    .andExpect(jsonPath("$.received", is(receiveds[i])))
                    .andExpect(jsonPath("$.result", is(results[i])))
                    .andDo(print());
        }
    }
}
