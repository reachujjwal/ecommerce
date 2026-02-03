package com.ujjwal.ecommerce.repository;

import com.ujjwal.ecommerce.model.Store;
import com.ujjwal.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.management.relation.Role;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);
    User findById(Long id);
    List<User> findByStore(Store store);
    List<User> findByBranchId(Long branchId);
}
