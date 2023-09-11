package com.shoeshelf.repository;

import com.shoeshelf.domain.Customer;
import com.shoeshelf.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findAllByCustomerOrderByCreatedDateDesc(Customer customer);
}
