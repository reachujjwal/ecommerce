package com.ujjwal.ecommerce.service;

import com.ujjwal.ecommerce.exceptions.UserException;
import com.ujjwal.ecommerce.payload.dto.ProductDTO;
import com.ujjwal.ecommerce.model.User;

import java.util.List;

public interface ProductService {

    ProductDTO createProduct(ProductDTO productDto, User user) throws Exception;
    ProductDTO updateProduct(Long id, ProductDTO productDto, User user) throws Exception;
    void deleteProduct(Long id, User user) throws Exception;
    List<ProductDTO> getProductByStoreId(Long storeId);
    List<ProductDTO> searchByKeyword(Long storeId , String keyword);



}
