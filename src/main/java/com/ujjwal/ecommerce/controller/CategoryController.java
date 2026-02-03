package com.ujjwal.ecommerce.controller;

import com.ujjwal.ecommerce.payload.dto.CategoryDTO;
import com.ujjwal.ecommerce.payload.response.ApiResponse;
import com.ujjwal.ecommerce.service.CategoryService;
import com.ujjwal.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<CategoryDTO> create(
            @RequestBody CategoryDTO categoryDTO) throws Exception {

        return ResponseEntity.ok(
                categoryService.createCategory(categoryDTO)
        );

    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<CategoryDTO>> getCategoriesByStoreId(
            @PathVariable Long storeId) throws Exception {

        return ResponseEntity.ok(
                categoryService.getAllCategoriesByStoreId(storeId)
        );

    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updatedCategory(
            @PathVariable Long id,
            @RequestBody CategoryDTO categoryDTO) throws Exception {

        return ResponseEntity.ok(
                categoryService.updateCategory(id,categoryDTO)
        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deletedCategory(
            @PathVariable Long id,
            @RequestBody CategoryDTO categoryDTO) throws Exception {
        categoryService.deleteCategory(id);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Category deleted successfully");
        apiResponse.setStatus("1");

        return ResponseEntity.ok(apiResponse);

    }


}
