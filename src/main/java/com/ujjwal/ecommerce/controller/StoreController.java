package com.ujjwal.ecommerce.controller;

import com.ujjwal.ecommerce.exceptions.UserException;
import com.ujjwal.ecommerce.model.User;
import com.ujjwal.ecommerce.payload.dto.StoreDTO;
import com.ujjwal.ecommerce.service.StoreService;
import com.ujjwal.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store")
public class StoreController {

    private final StoreService storeService;
    private final UserService userService;

    @PostMapping("/createStore")
    public ResponseEntity<StoreDTO> createStore(@RequestBody StoreDTO storeDto,
                                                @RequestHeader("authorization") String jwt) throws UserException {
        User user = userService.getUserFromJwtToken(jwt);
        return ResponseEntity.ok(storeService.createStore(storeDto,user));
    }

    @GetMapping("/getStore/{id}")
    public ResponseEntity<StoreDTO> getStoreById(@PathVariable Long id,
                                                 @RequestHeader("authorization") String jwt) throws Exception {
        User user = userService.getUserFromJwtToken(jwt);
        return ResponseEntity.ok(storeService.getStoreById(id));
    }

    @PostMapping("/getAllStores")
    public ResponseEntity<List<StoreDTO>> getAllStores(@RequestHeader("authorization") String jwt) throws Exception {
        return  ResponseEntity.ok(storeService.getAllStores());
    }
}
