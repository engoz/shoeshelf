package com.shoeshelf.repository;

import com.shoeshelf.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    List<Customer> findAll();
    Customer findByFirstName(String firstName);
    Customer findByLastName(String lastName);
    Customer findByEmail(String email);

}
