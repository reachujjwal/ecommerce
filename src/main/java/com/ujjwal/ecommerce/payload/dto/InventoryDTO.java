package com.ujjwal.ecommerce.payload.dto;

import com.ujjwal.ecommerce.model.Branch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryDTO {

    private Long id;

    private BranchDTO branch;

    private ProductDTO product;

    private Long branchId;
    private Long productId;

    private Integer quantity;

    private LocalDateTime lastUpdated;
}
