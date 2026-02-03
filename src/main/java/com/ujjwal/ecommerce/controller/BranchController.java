package com.ujjwal.ecommerce.controller;

import com.ujjwal.ecommerce.exceptions.UserException;
import com.ujjwal.ecommerce.payload.dto.BranchDTO;
import com.ujjwal.ecommerce.payload.response.ApiResponse;
import com.ujjwal.ecommerce.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/branches")
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<BranchDTO> createBranch(@RequestBody BranchDTO branchDTO) throws UserException {
        BranchDTO createdBranch = branchService.createBranch(branchDTO);
        return ResponseEntity.ok(createdBranch);
    }


    @GetMapping("/{id}")
    public ResponseEntity<BranchDTO> branchById(@PathVariable Long id) throws Exception {
        BranchDTO branchId = branchService.getBranchById(id);
        return ResponseEntity.ok(branchId);
    }


    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<BranchDTO>> getAllBranchesByStoreId(@PathVariable Long storeId) throws Exception {
        List<BranchDTO> branchLists = branchService.findAllBranchesByStoreId(storeId);
        return ResponseEntity.ok(branchLists);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchDTO> updateBranch(
            @PathVariable Long id,
            @RequestBody BranchDTO branchDTO
    ) throws Exception {

        BranchDTO updatedBranch = branchService.updateBranch(id, branchDTO);
        return ResponseEntity.ok(updatedBranch);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBranch(@PathVariable Long id) throws Exception {
        branchService.deleteBranch(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Branch has been deleted successfully.!");
        apiResponse.setStatus("1");

        return ResponseEntity.ok(apiResponse);

    }



}
