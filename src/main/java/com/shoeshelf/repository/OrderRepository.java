package com.shoeshelf.repository;

import com.shoeshelf.domain.Customer;
import com.shoeshelf.domain.Order;
import com.shoeshelf.status.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findAllByCustomerOrderByCreatedDateDesc(Customer customer);

    @Query("select o from Order o where o.status = ?1")
    List<Order> findByOrderStatus(OrderStatus status);
}
