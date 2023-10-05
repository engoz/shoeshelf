package com.shoeshelf.service;

import com.shoeshelf.domain.Order;
import com.shoeshelf.dto.OrderDto;
import com.shoeshelf.exceptions.InternelServerException;
import com.shoeshelf.exceptions.OrderNotFoundException;
import com.shoeshelf.repository.OrderItemRepository;
import com.shoeshelf.repository.OrderRepository;
import com.shoeshelf.util.OrderDtoConverters;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<OrderDto> getAllOrders() throws OrderNotFoundException {
        List<OrderDto> orderDtos = new ArrayList<>();
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()){
            throw new OrderNotFoundException("Order not found ");
        }
        for (Order order : orders) {
            OrderDto orderDto = convertDto(order);
            orderDtos.add(orderDto);
        }
        return orderDtos;
    }

    public OrderDto getById(Integer id) throws OrderNotFoundException {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()){
            throw new OrderNotFoundException("Order not found with id :" + id);
        }
        OrderDto orderDto =  convertDto(orderOptional.get());
        return orderDto;
    }

    private OrderDto convertDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setCreatedDate(order.getCreatedDate());
        orderDto.setCustomer(order.getCustomer());
        orderDto.setOrderItems(OrderDtoConverters.convertOrderItemsDto(order.getOrderItems()));
        orderDto.setTotalPrice(order.getTotalPrice());
        return orderDto;
    }



    public OrderDto update(OrderDto dto) throws OrderNotFoundException{
        Optional<Order> orderOptional = orderRepository.findById(dto.getId());
        if (orderOptional.isEmpty()){
            throw new OrderNotFoundException("Order not found with id :" + dto.getId());
        }
        Order order = orderOptional.get();
        order.setCreatedDate(new Date());
        order.setCustomer(dto.getCustomer());
        order.setOrderItems(OrderDtoConverters.convertOrderItems(dto.getOrderItems()));
        order.setTotalPrice(dto.getTotalPrice());
        orderRepository.save(order);
        return dto;
    }

    public void deleteOrder(Integer id) {
        try {
            orderRepository.deleteById(id);
        }catch (Exception ex){
            throw new InternelServerException(ex);

        }
    }


    public OrderDto createOrder(OrderDto dto) {
        if (dto == null)
            throw new NullPointerException();

        Order order = new Order();
        order.setCreatedDate(new Date());
        order.setCustomer(dto.getCustomer());
        order.setOrderItems(OrderDtoConverters.convertOrderItems(dto.getOrderItems()));
        order.setTotalPrice(dto.getTotalPrice());
        orderRepository.save(order);
        dto.setId(order.getId());
        return dto;
    }



}
