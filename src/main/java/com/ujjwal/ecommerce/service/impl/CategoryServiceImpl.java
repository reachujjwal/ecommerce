package com.ujjwal.ecommerce.service.impl;

import com.ujjwal.ecommerce.domain.UserRole;
import com.ujjwal.ecommerce.exceptions.UserException;
import com.ujjwal.ecommerce.mapper.CategoryMapper;
import com.ujjwal.ecommerce.model.Category;
import com.ujjwal.ecommerce.model.Store;
import com.ujjwal.ecommerce.model.User;
import com.ujjwal.ecommerce.payload.dto.CategoryDTO;
import com.ujjwal.ecommerce.repository.CategoryRepository;
import com.ujjwal.ecommerce.repository.StoreRepository;
import com.ujjwal.ecommerce.service.CategoryService;
import com.ujjwal.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final StoreRepository storeRepository;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) throws Exception {
        User user = userService.getCurrentUser();
        Store store = storeRepository.findById(categoryDTO.getStoreId()).orElseThrow(
                ()->new Exception("Store not found")
        );

        Category category = Category.builder()
                .store(store)
                .name(categoryDTO.getName())
                .build();

        checkAuthority(user,category.getStore());
        Category saved = categoryRepository.save(category);
        return CategoryMapper.toDTO(saved);
    }

    @Override
    public List<CategoryDTO> getAllCategoriesByStoreId(Long storeId) {
        List<Category> categories = categoryRepository.findByStoreId(storeId);
        return categories.stream()
                .map(
                        CategoryMapper::toDTO
                ).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) throws Exception {
        Category category = categoryRepository.findById(id).orElseThrow(
                ()-> new Exception("Category not found")
        );
        User user = userService.getCurrentUser();
        category.setName(categoryDTO.getName());
        checkAuthority(user,category.getStore());
        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) throws Exception {
        Category category = categoryRepository.findById(id).orElseThrow(
                ()-> new Exception("Category not found")
        );
        User user = userService.getCurrentUser();
        checkAuthority(user,category.getStore());
        categoryRepository.delete(category);
    }

    private void checkAuthority(User user,Store store) throws Exception {
        boolean isAdmin     = user.getRole().equals(UserRole.ROLE_STORE_ADMIN);
        boolean isManager   = user.getRole().equals(UserRole.ROLE_STORE_MANAGER);
        boolean isSameStore = user.equals(store.getStoreAdmin());

        if(!(isAdmin && isSameStore) && !isManager){
            throw new Exception("You don't have permission to manage this category");
        }

    }
}
