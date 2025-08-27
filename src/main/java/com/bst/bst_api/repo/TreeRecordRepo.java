package com.bst.bst_api.repo;

import com.bst.bst_api.entity.TreeRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreeRecordRepo extends JpaRepository<TreeRecord, Long> {
}
