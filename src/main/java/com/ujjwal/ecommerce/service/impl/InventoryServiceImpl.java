package com.ujjwal.ecommerce.service.impl;

import com.ujjwal.ecommerce.mapper.InventoryMapper;
import com.ujjwal.ecommerce.model.Branch;
import com.ujjwal.ecommerce.model.Inventory;
import com.ujjwal.ecommerce.model.Product;
import com.ujjwal.ecommerce.payload.dto.InventoryDTO;
import com.ujjwal.ecommerce.repository.BranchRepository;
import com.ujjwal.ecommerce.repository.InventoryRepository;
import com.ujjwal.ecommerce.repository.ProductRepository;
import com.ujjwal.ecommerce.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;

    @Override
    public InventoryDTO createInventory(InventoryDTO inventoryDTO) throws Exception {
        Branch branch = branchRepository.findById(inventoryDTO.getBranchId()).orElseThrow(
                ()-> new Exception("Branch not found.!")
        );
        Product product = productRepository.findById(inventoryDTO.getProductId()).orElseThrow(
                ()-> new Exception("Product not found.!")
        );

        Inventory inventory = InventoryMapper.toEntity(inventoryDTO, branch, product);
        Inventory inventorySaved = inventoryRepository.save(inventory);

        return InventoryMapper.toDto(inventorySaved);
    }

    @Override
    public InventoryDTO updateInventory(Long id , InventoryDTO inventoryDTO) throws Exception {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(
                ()->new Exception("Inventory not found")
        );
        inventory.setQuantity(inventoryDTO.getQuantity());
        Inventory updatedInventory = inventoryRepository.save(inventory);
        return InventoryMapper.toDto(updatedInventory);
    }

    @Override
    public void deleteInventory(Long id) throws Exception {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(
                ()->new Exception("Inventory not found")
        );
        inventoryRepository.delete(inventory);
    }

    @Override
    public InventoryDTO getInventoryById(Long id) throws Exception {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(
                ()->new Exception("Inventory not found")
        );
        return InventoryMapper.toDto(inventory);
    }

    @Override
    public InventoryDTO getInventoryByProductIdAndBranchId(Long productId, Long branchId) {
        Inventory inventory = inventoryRepository.findByProductIdAndBranchId(productId, branchId);
        return InventoryMapper.toDto(inventory);
    }

    @Override
    public List<InventoryDTO> getAllInventoryByBranch(Long branchId) {
        List<Inventory> inventories = inventoryRepository.findByBranchId(branchId);

        return inventories.stream()
                .map(InventoryMapper::toDto)
                .collect(Collectors.toList());
    }
}
