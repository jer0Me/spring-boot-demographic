package com.jerome.demographic.integrations;

import com.jerome.demographic.person.PersonController;
import com.jerome.demographic.person.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService mockPersonService;

    @Test
    public void shouldBeAvailablePersonListUrl() throws Exception {
        this.mockMvc.perform(get("/persons/list")).andExpect(status().isOk());
    }

    @Test
    public void shouldBeAvailablePersonFormUrl() throws Exception {
        this.mockMvc.perform(get("/persons/add_person")).andExpect(status().isOk());
    }

    @Test
    public void shouldBeAvailableAddPersonUrl() throws Exception {
        this.mockMvc.perform(post("/persons/add_person")).andExpect(status().is3xxRedirection());
    }
}
