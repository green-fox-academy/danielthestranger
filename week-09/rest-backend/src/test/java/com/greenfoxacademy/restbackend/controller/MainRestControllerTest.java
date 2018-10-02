package com.greenfoxacademy.restbackend.controller;

import com.greenfoxacademy.restbackend.service.ArrayActionService;
import com.greenfoxacademy.restbackend.service.DoUntilService;
import com.greenfoxacademy.restbackend.service.LogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
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
    private String greeterEndPoint ="/greeter";
    private String arraysEndPoint ="/arrays";

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
        String[] urlTemplates = {
                            endpoint,
                            endpoint+"?",
                            endpoint+"?input=",
        };

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
        String[] urlTemplates = {
                            endpoint+"?input=15",
                            endpoint+"?input=0",
        };
        Double[] receiveds = {
                            15.,
                            0.,
        };
        Double[] results = {
                            30.,
                            0.,
        };

        for (int i = 0; i < urlTemplates.length; i++) {
            mockMvc.perform(get(urlTemplates[i]))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(contentType))
                    .andExpect(jsonPath("$.received", is(receiveds[i])))
                    .andExpect(jsonPath("$.result", is(results[i])))
                    .andDo(print());
        }
    }


    @Test
    public void greeterReturnsError_on_noParam() throws Exception {
        String endpoint = greeterEndPoint;
        String nameErrorMsg = "Please provide a name!";
        String titleErrorMsg = "Please provide a title!";

        String[] urlTemplates = {
                            endpoint,
                            endpoint+"?",
                            endpoint+"?name=",
                            endpoint+"?name=Petike",
                            endpoint+"?name=Petike&title=",
        };
        String[] errorMsgs = {
                            nameErrorMsg,
                            nameErrorMsg,
                            nameErrorMsg,
                            titleErrorMsg,
                            titleErrorMsg,
        };

        for (int i = 0; i < urlTemplates.length; i++) {
            mockMvc.perform(get(urlTemplates[i]))
                    .andExpect(status().isAccepted())
                    .andExpect(content().contentType(contentType))
                    .andExpect(jsonPath(jsonErrorPath, is(errorMsgs[i])))
                    .andDo(print());
        }
    }


    @Test
    public void greeterReturnsJson_on_validInput() throws Exception {
        String endpoint = greeterEndPoint;
        String[] urlTemplates = {
                endpoint+"?name=Petike&title=student",
                endpoint+"?name=2&title=3.2",
        };
        String[] results = {
                "Oh, hi there Petike, my dear student!",
                "Oh, hi there 2, my dear 3.2!",
        };

        for (int i = 0; i < urlTemplates.length; i++) {
            mockMvc.perform(get(urlTemplates[i]))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(contentType))
                    .andExpect(jsonPath("$.welcome_message", is(results[i])))
                    .andDo(print());
        }
    }

    @Test
    public void arraysReturnsError_on_noParam() throws Exception {
        String endpoint = arraysEndPoint;
        String missingInputMsg = "Please provide what to do with the numbers!";

        String[] jsonPosts = {
                "",
                "{}",
                "{\"numbers\": [1,2,5,10]}",
                "{\"what\": \"sum\"}",
        };

        for (String jsonPost : jsonPosts) {
            mockMvc.perform(post(endpoint)
                    .contentType(contentType)
                    .content(jsonPost))
                    .andExpect(status().isAccepted())
                    .andExpect(content().contentType(contentType))
                    .andExpect(jsonPath(jsonErrorPath, is(missingInputMsg)))
                    .andDo(print());
        }
    }

    @Test
    public void arraysReturnsError_on_unsupportedAction() throws Exception {
        String endpoint = arraysEndPoint;
        String unsupportedAction = "nowaythiswouldexist";
        String errorMsg = "Unsupported action: " + unsupportedAction;

        when(arrayActionService.doArrayAction(eq(unsupportedAction), anyList())).thenThrow(new IllegalArgumentException(errorMsg));

        String[] jsonPosts = {
                "{\"what\": \"" + unsupportedAction + "\", \"numbers\": [1,2,5,10]}",
        };

        for (String jsonPost : jsonPosts) {
            mockMvc.perform(post(endpoint)
                    .contentType(contentType)
                    .content(jsonPost))
                    .andExpect(status().isAccepted())
                    .andExpect(content().contentType(contentType))
                    .andExpect(jsonPath(jsonErrorPath, is(errorMsg)))
                    .andDo(print());
        }
    }
}
