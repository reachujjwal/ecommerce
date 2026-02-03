package com.ujjwal.ecommerce.mapper;

import com.ujjwal.ecommerce.model.Category;
import com.ujjwal.ecommerce.model.Product;
import com.ujjwal.ecommerce.payload.dto.ProductDTO;
import com.ujjwal.ecommerce.model.Store;

public class ProductMapper {

    public static ProductDTO toDTO(Product product){
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .sku(product.getSku())
                .description(product.getDescription())
                .mrp(product.getMrp())
                .sellingPrice(product.getSellingPrice())
                .brand(product.getBrand())
                .storeId(product.getStore()!=null?product.getStore().getId():null)
                .image(product.getImage())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .category(CategoryMapper.toDTO(product.getCategory()))
                .build();
    }

    public static Product toEntity(ProductDTO productDTO , Store store , Category category){
        return Product.builder()
                .name(productDTO.getName())
                .sku(productDTO.getSku())
                .description(productDTO.getDescription())
                .mrp(productDTO.getMrp())
                .sellingPrice(productDTO.getSellingPrice())
                .brand(productDTO.getBrand())
                .store(store)
                .category(category)
                .build();
    }
}
