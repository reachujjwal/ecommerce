package com.ujjwal.ecommerce.repository;

import com.ujjwal.ecommerce.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch,Long> {
    List<Branch> findByStoreId(Long storeId);
}
