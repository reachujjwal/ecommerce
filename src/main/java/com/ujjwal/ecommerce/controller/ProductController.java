package com.ujjwal.ecommerce.controller;

import com.ujjwal.ecommerce.exceptions.UserException;
import com.ujjwal.ecommerce.payload.dto.ProductDTO;
import com.ujjwal.ecommerce.model.User;
import com.ujjwal.ecommerce.payload.response.ApiResponse;
import com.ujjwal.ecommerce.service.ProductService;
import com.ujjwal.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO,
                                                    @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.getUserFromJwtToken(jwt);
        return ResponseEntity.ok(
                            productService.createProduct(
                                    productDTO,user
                            )
        );
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<ProductDTO>> getByStoreId(@PathVariable Long storeId,
                                                    @RequestHeader("Authorization") String jwt) throws UserException {
        return ResponseEntity.ok(
                productService.getProductByStoreId(storeId)
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id,
                                                    @RequestBody ProductDTO productDTO,
                                                    @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.getUserFromJwtToken(jwt);

        return ResponseEntity.ok(
                productService.updateProduct(id,productDTO,user)
        );
    }

    @GetMapping("/store/{storeId}/search")
    public ResponseEntity<List<ProductDTO>> searchByKeyword(@PathVariable Long storeId,
                                                         @RequestParam String keyword,
                                                         @RequestHeader("Authorization") String jwt) throws UserException {
        return ResponseEntity.ok(
                productService.searchByKeyword(storeId, keyword)
        );
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id,
                                                    @RequestHeader("Authorization") String jwt) throws Exception {

        ApiResponse apiResponse = new ApiResponse();
        User user = userService.getUserFromJwtToken(jwt);
        productService.deleteProduct(id,user);

        apiResponse.setStatus("1");
        apiResponse.setMessage("Product deleted successfully");

        return ResponseEntity.ok(apiResponse);

    }
}
