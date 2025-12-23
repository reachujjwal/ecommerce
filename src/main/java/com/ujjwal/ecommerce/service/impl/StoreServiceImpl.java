package com.ujjwal.ecommerce.service.impl;

import com.ujjwal.ecommerce.domain.StoreStatus;
import com.ujjwal.ecommerce.exceptions.UserException;
import com.ujjwal.ecommerce.mapper.StoreMapper;
import com.ujjwal.ecommerce.model.Store;
import com.ujjwal.ecommerce.model.StoreContact;
import com.ujjwal.ecommerce.model.User;
import com.ujjwal.ecommerce.payload.dto.StoreDTO;
import com.ujjwal.ecommerce.repository.StoreRepository;
import com.ujjwal.ecommerce.service.StoreService;
import com.ujjwal.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final UserService userService;

    @Override
    public StoreDTO createStore(StoreDTO storeDTO, User user) {
        Store store = StoreMapper.toEntity(storeDTO,user);
        return  StoreMapper.toDTO(storeRepository.save(store));
    }

    @Override
    public StoreDTO getStoreById(Long id) throws UserException {
        Store store = storeRepository.findById(id).orElseThrow(
                ()->new UserException("Store not found..!")
        );
        return  StoreMapper.toDTO(store);
    }

    @Override
    public List<StoreDTO> getAllStores() {
        List<Store> dtos = storeRepository.findAll();
        return dtos.stream().map(StoreMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Store getStoreByAdmin() throws UserException {
        User admin =  userService.getCurrentUser();
        return storeRepository.findByStoreAdminId(admin.getId());
    }

    @Override
    public StoreDTO updateStore(Long id, StoreDTO storeDTO) throws UserException {
        User currentUser = userService.getCurrentUser();
        Store existing = storeRepository.findByStoreAdminId(currentUser.getId());
        if(existing==null){
            throw new UserException("Store admin id not found.!");
        }

        existing.setBrand(storeDTO.getBrand());
        existing.setDescription(storeDTO.getDescription());
        existing.setStatus(storeDTO.getStatus());
        if(existing.getStoreType()==null){
            existing.setStoreType(storeDTO.getStoreType());
        }

        if(storeDTO.getContact()!=null){
            StoreContact contact = StoreContact.builder()
                    .address(storeDTO.getContact().getAddress())
                    .phone(storeDTO.getContact().getEmail())
                    .build();
            existing.setContact(contact);
        }
        Store updatedStore = storeRepository.save(existing);
        return StoreMapper.toDTO(updatedStore);
    }

    @Override
    public void deleteStore(Long id) throws UserException {
        Store store = getStoreByAdmin();
        storeRepository.delete(store);
    }

    @Override
    public StoreDTO getStoreByEmployee() throws UserException {
        User currentUser = userService.getCurrentUser();
        if(currentUser == null){
            throw new UserException("You don't have permission to access this store.!");

        }
        return StoreMapper.toDTO(currentUser.getStore());
    }

    @Override
    public StoreDTO moderateStore(Long id, StoreStatus status) throws UserException {
        Store store = storeRepository.findById(id).orElseThrow(
                ()->new UserException("Store not found..!")
        );
        store.setStatus(status);
        Store updatedStore = storeRepository.save(store);
        return StoreMapper.toDTO(updatedStore);
    }

}
