package com.ujjwal.ecommerce.service;

import com.ujjwal.ecommerce.exceptions.UserException;
import com.ujjwal.ecommerce.payload.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO) throws Exception;
    List<CategoryDTO> getAllCategoriesByStoreId(Long storeId);
    CategoryDTO updateCategory(Long id , CategoryDTO categoryDTO) throws Exception;
    void deleteCategory(Long id) throws Exception;
}
