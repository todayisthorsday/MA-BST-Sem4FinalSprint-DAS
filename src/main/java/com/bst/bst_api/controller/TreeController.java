package com.bst.bst_api.controller;

import com.bst.bst_api.entity.TreeRecord;
import com.bst.bst_api.repo.TreeRecordRepo;
import com.bst.bst_api.service.BSTService;
import com.bst.bst_api.model.BSTNode;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/")
public class TreeController {

    private final BSTService bstService;
    private final TreeRecordRepo repo;

    public TreeController(BSTService bstService, TreeRecordRepo repo) {
        this.bstService = bstService;
        this.repo = repo;
    }

    @PostMapping("/process-numbers")
    public Map<String, Object> process(@RequestBody Map<String, String> body) {
        String numbers = body.get("numbers");
        List<Integer> nums = bstService.parseInput(numbers);
        BSTNode root = bstService.buildBalancedBST(nums);
        Map<String, Object> treeJson = bstService.toMap(root);

        TreeRecord rec = new TreeRecord();
        rec.setInputNumbers(numbers);
        rec.setTreeJson(treeJson.toString()); // quick + dirty (could use Jackson)
        repo.save(rec);

        return treeJson;
    }

    @GetMapping("/previous-trees")
    public List<TreeRecord> all() {
        return repo.findAll();
    }
}
