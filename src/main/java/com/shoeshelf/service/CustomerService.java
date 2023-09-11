package com.shoeshelf.service;

import com.shoeshelf.domain.Customer;
import com.shoeshelf.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public List<Customer> listCustomers(){
        return customerRepository.findAll();
    }
}
