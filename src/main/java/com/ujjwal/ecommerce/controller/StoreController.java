package com.ujjwal.ecommerce.controller;

import com.ujjwal.ecommerce.domain.StoreStatus;
import com.ujjwal.ecommerce.exceptions.UserException;
import com.ujjwal.ecommerce.mapper.StoreMapper;
import com.ujjwal.ecommerce.model.User;
import com.ujjwal.ecommerce.payload.dto.StoreDTO;
import com.ujjwal.ecommerce.payload.response.ApiResponse;
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

    @PostMapping()
    public ResponseEntity<StoreDTO> createStore(@RequestBody StoreDTO storeDto,
                                                @RequestHeader("authorization") String jwt) throws UserException {
        User user = userService.getUserFromJwtToken(jwt);
        return ResponseEntity.ok(storeService.createStore(storeDto,user));
    }

    @GetMapping()
    public ResponseEntity<List<StoreDTO>> getAllStores(@RequestHeader("authorization") String jwt) throws UserException {
        return  ResponseEntity.ok(storeService.getAllStores());
    }


    @GetMapping("/admin")
    public ResponseEntity<StoreDTO> getStoreByAdmin(
                                    @RequestHeader("authorization") String jwt) throws UserException {
        return  ResponseEntity.ok(StoreMapper.toDTO(storeService.getStoreByAdmin()));
    }

    @GetMapping("/employee")
    public ResponseEntity<StoreDTO> getStoreByEmployee(
            @RequestHeader("authorization") String jwt) throws UserException {
        return  ResponseEntity.ok(storeService.getStoreByEmployee());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreDTO> updateStore(
            @PathVariable Long id,
            @RequestBody StoreDTO storeDTO) throws UserException {
        return  ResponseEntity.ok(storeService.updateStore(id,storeDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteStore(@PathVariable Long id) throws UserException {

        storeService.deleteStore(id);

        ApiResponse apiResponse =  new  ApiResponse();
        apiResponse.setMessage("1");
        apiResponse.setMessage("Store deleted successfully");

        return  ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreDTO> getStoreById(@PathVariable Long id,
                                                 @RequestHeader("authorization") String jwt) throws UserException {
        User user = userService.getUserFromJwtToken(jwt);
        return ResponseEntity.ok(storeService.getStoreById(id));
    }

    @PutMapping("/{id}/moderate")
    public ResponseEntity<StoreDTO> moderateStore(@PathVariable Long id,
                                                     @RequestParam StoreStatus status) throws UserException {

        /*StoreDTO sucess = storeService.moderateStore(id,status);
        return  ResponseEntity.ok(sucess);*/

        return ResponseEntity.ok(storeService.moderateStore(id,status));
    }

}
