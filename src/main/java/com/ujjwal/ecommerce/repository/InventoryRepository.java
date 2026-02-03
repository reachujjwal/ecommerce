package com.ujjwal.ecommerce.repository;

import com.ujjwal.ecommerce.model.Inventory;
import com.ujjwal.ecommerce.payload.dto.BranchDTO;
import com.ujjwal.ecommerce.payload.dto.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    Inventory findByProductIdAndBranchId(Long productId,Long branchId);
    List<Inventory> findByBranchId(Long branchId);

}
