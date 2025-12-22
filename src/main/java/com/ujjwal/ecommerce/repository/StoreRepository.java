package com.ujjwal.ecommerce.repository;

import com.ujjwal.ecommerce.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store,Long> {
    Store findByStoreAdminId(Long adminId);
}
