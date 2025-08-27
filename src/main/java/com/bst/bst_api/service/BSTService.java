package com.bst.bst_api.service;

import com.bst.bst_api.model.BSTNode;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class BSTService {

    public List<Integer> parseInput(String input) {
        String[] parts = input.split("[,\\s]+");
        List<Integer> nums = new ArrayList<>();
        for (String p : parts) {
            try {
                nums.add(Integer.parseInt(p.trim()));
            } catch (NumberFormatException ignored) {}
        }
        return nums;
    }

    public BSTNode insert(BSTNode root, int val) {
        if (root == null) return new BSTNode(val);
        if (val < root.getValue()) {
            root.setLeft(insert(root.getLeft(), val));
        } else {
            root.setRight(insert(root.getRight(), val));
        }
        return root;
    }

    public BSTNode buildBST(List<Integer> nums) {
        BSTNode root = null;
        for (int n : nums) {
            root = insert(root, n);
        }
        return root;
    }

    // simple map structure to be JSON-friendly
    public Map<String, Object> toMap(BSTNode node) {
        if (node == null) return null;
        Map<String, Object> map = new HashMap<>();
        map.put("value", node.getValue());
        map.put("left", toMap(node.getLeft()));
        map.put("right", toMap(node.getRight()));
        return map;
    }
}
