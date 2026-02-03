package com.ujjwal.ecommerce.controller;

import com.ujjwal.ecommerce.model.Inventory;
import com.ujjwal.ecommerce.payload.dto.InventoryDTO;
import com.ujjwal.ecommerce.payload.response.ApiResponse;
import com.ujjwal.ecommerce.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventories")
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryDTO> create(@RequestBody InventoryDTO inventoryDTO) throws Exception {
        return ResponseEntity.ok(
                inventoryService.createInventory(inventoryDTO)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryDTO> update(
            @PathVariable Long id,
            @RequestBody InventoryDTO inventoryDTO
    ) throws Exception {

        return ResponseEntity.ok(
                inventoryService.updateInventory(id, inventoryDTO)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(
            @PathVariable Long id
    ) throws Exception {
        ApiResponse apiResponse = new ApiResponse();
        inventoryService.deleteInventory(id);
        apiResponse.setMessage("Inventory deleted successfully.!");
        apiResponse.setStatus("1");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/branch/{branchId}/product/{productId}")
    public ResponseEntity<InventoryDTO> getInventoryByProductAndBranchId(
            @PathVariable Long productId,
            @PathVariable Long branchId
    ) throws Exception {

        return ResponseEntity.ok(
               inventoryService.getInventoryByProductIdAndBranchId(productId,branchId)
        );
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<InventoryDTO>> getInventoryByBranch(
            @PathVariable Long branchId
    ) throws Exception {

        return ResponseEntity.ok(
                inventoryService.getAllInventoryByBranch(branchId)
        );
    }



}
