package com.ujjwal.ecommerce.service.impl;

import com.ujjwal.ecommerce.exceptions.UserException;
import com.ujjwal.ecommerce.mapper.ProductMapper;
import com.ujjwal.ecommerce.model.Category;
import com.ujjwal.ecommerce.model.Product;
import com.ujjwal.ecommerce.payload.dto.ProductDTO;
import com.ujjwal.ecommerce.model.Store;
import com.ujjwal.ecommerce.model.User;
import com.ujjwal.ecommerce.repository.CategoryRepository;
import com.ujjwal.ecommerce.repository.ProductRepository;
import com.ujjwal.ecommerce.repository.StoreRepository;
import com.ujjwal.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductDTO createProduct(ProductDTO productDto, User user) throws Exception {
        Store store = storeRepository.findById(
                productDto.getStoreId()
        ).orElseThrow(
                ()-> new Exception("Store not found.!")
        );

        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(
                ()-> new Exception("Category not found.!")
        );

        Product product = ProductMapper.toEntity(productDto, store,category);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDTO(savedProduct);

    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDto, User user) throws Exception {
         Product product = productRepository.findById(id).orElseThrow(
                 ()-> new Exception("Product not found")
         );

         product.setName(productDto.getName());
         product.setSku(productDto.getSku());
         product.setDescription(productDto.getDescription());
         product.setMrp(product.getMrp());
         product.setSellingPrice(product.getSellingPrice());
         product.setImage(product.getImage());
         product.setBrand(product.getBrand());
         product.setUpdatedAt(LocalDateTime.now());

        if(productDto.getCategoryId()!=null){
            Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(
                    ()-> new Exception("Category not found.!")
            );
            product.setCategory(category);
        }

         Product updatedProduct = productRepository.save(product);
         return ProductMapper.toDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id, User user) throws Exception {
         Product product = productRepository.findById(id).orElseThrow(
                 ()-> new Exception("Product id is not found")
         );
         productRepository.delete(product);
    }

    @Override
    public List<ProductDTO> getProductByStoreId(Long storeId) {
        List<Product> products = productRepository.findByStoreId(storeId);
        return products.stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> searchByKeyword(Long storeId, String keyword) {
        List<Product> products = Collections.singletonList(productRepository.searchByKeyword(storeId, keyword));
        return products.stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }
}
