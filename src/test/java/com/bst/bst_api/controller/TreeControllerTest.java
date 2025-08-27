package com.bst.bst_api.controller;

import com.bst.bst_api.entity.TreeRecord;
import com.bst.bst_api.repo.TreeRecordRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Map;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals; // Added import for assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue; // Added import for assertTrue

@SpringBootTest
@AutoConfigureMockMvc
class TreeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TreeRecordRepo repo;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testProcessNumbersEndpoint() throws Exception {
        Map<String, String> payload = Map.of("numbers", "2 1 3");

        mockMvc.perform(post("/process-numbers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(2))
                .andExpect(jsonPath("$.left.value").value(1))
                .andExpect(jsonPath("$.right.value").value(3));

        TreeRecord rec = repo.findAll().get(0);
        assertEquals("2 1 3", rec.getInputNumbers());
        assertTrue(rec.getTreeJson().contains("2"));
    }
}
