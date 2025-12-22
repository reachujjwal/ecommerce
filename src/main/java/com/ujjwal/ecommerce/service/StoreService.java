package com.ujjwal.ecommerce.service;

import com.ujjwal.ecommerce.exceptions.UserException;
import com.ujjwal.ecommerce.model.Store;
import com.ujjwal.ecommerce.model.User;
import com.ujjwal.ecommerce.payload.dto.StoreDTO;

import java.util.List;

public interface StoreService {

    StoreDTO createStore(StoreDTO storeDTO, User user);
    StoreDTO getStoreById(Long id) throws Exception;
    List<StoreDTO> getAllStores();
    Store getStoreByAdmin() throws UserException;
    StoreDTO updateStore(Long id , StoreDTO storeDTO) throws UserException;
    void deleteStore(Long id) throws UserException;
    StoreDTO getStoreByEmployee() throws Exception;
}
