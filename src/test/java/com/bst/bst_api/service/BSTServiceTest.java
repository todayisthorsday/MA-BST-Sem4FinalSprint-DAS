package com.bst.bst_api.service;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BSTServiceTest {

    private final BSTService service = new BSTService();

    @Test
    void testParseInput_validAndInvalidNumbers() {
        List<Integer> result = service.parseInput("1, 2, three, 4 5");
        assertEquals(List.of(1, 2, 4, 5), result);
    }
}
