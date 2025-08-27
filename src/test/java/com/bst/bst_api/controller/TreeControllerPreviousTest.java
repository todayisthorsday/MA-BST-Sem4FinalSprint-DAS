package com.bst.bst_api.controller;

import com.bst.bst_api.entity.TreeRecord;
import com.bst.bst_api.repo.TreeRecordRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TreeControllerPreviousTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TreeRecordRepo repo;

    @BeforeEach
    void setup() {
        repo.deleteAll(); // clear DB before each test
        TreeRecord rec = new TreeRecord();
        rec.setInputNumbers("5 3 7");
        rec.setTreeJson("{\"value\":5,\"left\":{\"value\":3},\"right\":{\"value\":7}}");
        repo.save(rec);
    }

    @Test
    void testPreviousTreesEndpoint() throws Exception {
        mockMvc.perform(get("/previous-trees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].inputNumbers").value("5 3 7"))
                .andExpect(jsonPath("$[0].treeJson").value("{\"value\":5,\"left\":{\"value\":3},\"right\":{\"value\":7}}"));
    }
}
