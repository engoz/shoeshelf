package com.shoeshelf.service;

import com.shoeshelf.domain.Customer;
import com.shoeshelf.domain.Order;
import com.shoeshelf.exceptions.OrderNotFoundException;
import com.shoeshelf.repository.OrderItemRepository;
import com.shoeshelf.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;


    public void placeOrder(Customer customer) {


        // create the order and save it
        Order newOrder = new Order();
        newOrder.setCreatedDate(new Date());
        newOrder.setCustomer(customer);
        orderRepository.save(newOrder);

    }

    public List<Order> listOrders(Customer customer) {
        return orderRepository.findAllByCustomerOrderByCreatedDateDesc(customer);
    }


    public Order getOrder(Integer orderId) throws OrderNotFoundException {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            return order.get();
        }
        throw new OrderNotFoundException("Order not found");
    }
}
