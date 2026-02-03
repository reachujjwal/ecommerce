package com.ujjwal.ecommerce.service.impl;

import com.ujjwal.ecommerce.model.Customer;
import com.ujjwal.ecommerce.repository.CustomerRepository;
import com.ujjwal.ecommerce.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;


    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) throws Exception {
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(
                ()-> new Exception("Customer not found with id " + id)
        );
        existingCustomer.setFullName(customer.getFullName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setPhone(customer.getPhone());

        return customerRepository.save(existingCustomer);
    }

    @Override
    public void deleteCustomer(Long id) throws Exception {
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(
                ()-> new Exception("Customer not found with id " + id)
        );
        customerRepository.delete(existingCustomer);
    }

    @Override
    public Customer getCustomer(Long id) throws Exception {
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(
                ()-> new Exception("Customer not found with id " + id)
        );
        return existingCustomer;
    }

    @Override
    public List<Customer> getAllCustomers() throws Exception {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> searchCustomer(String keyword) throws Exception {
        return customerRepository.findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
    }
}
