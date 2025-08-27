package com.bst.bst_api.controller;

import com.bst.bst_api.entity.TreeRecord;
import com.bst.bst_api.repo.TreeRecordRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) 
// Ensures a fresh H2 DB for each test
class TreeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TreeRecordRepo repo;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void clearDatabase() {
        repo.deleteAll(); // clear DB before each test
    }

    @Test
    void testProcessNumbersEndpoint() throws Exception {
        Map<String, String> payload = Map.of("numbers", "2 1 3");

        // Send POST request to /process-numbers
        mockMvc.perform(post("/process-numbers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(2))
                .andExpect(jsonPath("$.left.value").value(1))
                .andExpect(jsonPath("$.right.value").value(3));

        // Ensure record was saved in H2
        var records = repo.findAll();
        assertThat(records).hasSize(1);

        TreeRecord rec = records.get(0);
        assertThat(rec.getInputNumbers()).isEqualTo("2 1 3");
        assertThat(rec.getTreeJson()).contains("2");
        assertThat(rec.getTreeJson()).contains("1");
        assertThat(rec.getTreeJson()).contains("3");
    }
}
