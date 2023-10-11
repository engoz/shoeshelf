package com.shoeshelf.service;

import com.shoeshelf.domain.Customer;
import com.shoeshelf.domain.Order;
import com.shoeshelf.domain.OrderItem;
import com.shoeshelf.domain.Product;
import com.shoeshelf.dto.order.*;
import com.shoeshelf.exceptions.*;
import com.shoeshelf.repository.CustomerRepository;
import com.shoeshelf.repository.OrderItemRepository;
import com.shoeshelf.repository.OrderRepository;
import com.shoeshelf.repository.ProductRepository;
import com.shoeshelf.status.OrderStatus;
import com.shoeshelf.util.OrderDtoConverters;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {


    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public OrderDto createOrder(OrderCreateDto dto) throws CustomerNotFoundExceptions, ProductNotFoundException, ProductQuantityException {
        if (dto == null)
            throw new NullPointerException();

        Optional<Customer> customerOptional = customerRepository.findById(dto.getCustomerId());

        if (customerOptional.isEmpty())
            throw new CustomerNotFoundExceptions("Customer Not Found");

        if (dto.getProductIds().isEmpty())
            throw new ProductNotFoundException("Products not found exception");

        Order order = new Order();
        order.setCustomer(customerOptional.get());
        order.setStatus(OrderStatus.Completed);
        orderRepository.save(order);
        double totalPrice = 0.0;
        List<OrderItemCreateDto> orderItemCreateDtos = dto.getProductIds();
        for (var orderItemCreateDto : orderItemCreateDtos){

            Optional<Product> productOptional = productRepository.findById(orderItemCreateDto.getProductId());
            if(productOptional.isEmpty())
                throw new ProductNotFoundException("Products not found exception");

            Product product = productOptional.get();

            boolean checkProductInventoryQuantity = checkProductInventoryQuantity(product, orderItemCreateDto.getQuantity());

            if(!checkProductInventoryQuantity)
                throw new ProductQuantityException("Products not found exception");

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setPrice(product.getSellPrice());
            orderItem.setQuantity(orderItem.getQuantity());
            orderItem.setOrder(order);
            totalPrice += product.getSellPrice() * orderItem.getQuantity();
            orderItemRepository.save(orderItem);
            product.setQuantity(product.getQuantity()-orderItem.getQuantity());
            productRepository.save(product);
        }
        order.setTotalPrice(totalPrice);
        return OrderDtoConverters.convertOrderToDto(order);
    }

    public OrderDto update(OrderUpdateDto dto) throws OrderNotFoundException, CustomerNotFoundExceptions, ProductNotFoundException, ProductQuantityException {
        Optional<Order> orderOptional = orderRepository.findById(dto.getId());
        if (orderOptional.isEmpty()){
            throw new OrderNotFoundException("Order not found with id :" + dto.getId());
        }

        Optional<Customer> customerOptional = customerRepository.findById(dto.getCustomerId());

        if (customerOptional.isEmpty())
            throw new CustomerNotFoundExceptions("Customer Not Found");

        if (dto.getOrderItemUpdateDtos().isEmpty())
            throw new ProductNotFoundException("Products not found exception");


        Order order = orderOptional.get();
        order.setModifiedDate(LocalDateTime.now());
        order.setCustomer(customerOptional.get());
        orderRepository.save(order);

        double totalPrice = 0.0;
        List<OrderItemUpdateDto> orderItemUpdateDtos = dto.getOrderItemUpdateDtos();
        List<OrderItem> orderItems = order.getOrderItems();
        for(OrderItem orderItem : orderItems) {
            for (OrderItemUpdateDto orderItemUpdateDto :orderItemUpdateDtos ) {
                if (orderItem.getId().equals(orderItemUpdateDto.getId())){
                    if (orderItemUpdateDto.getQuantity() > orderItem.getQuantity()){
                        Product product = orderItem.getProduct();
                        try {
                            Integer diffQuantity = orderItemUpdateDto.getQuantity() - orderItem.getQuantity();
                            boolean availableProduct = checkProductInventoryQuantity(product, diffQuantity);
                            if(availableProduct) {
                                product.setQuantity(product.getQuantity() - diffQuantity);
                                productRepository.save(product);
                            }

                            orderItem.setQuantity(orderItemUpdateDto.getQuantity());
                            totalPrice += product.getSellPrice() * orderItemUpdateDto.getQuantity();
                        } catch (ProductNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }else if (orderItemUpdateDto.getQuantity() < orderItem.getQuantity()){
                        Product product = orderItem.getProduct();
                            Integer diffQuantity = orderItemUpdateDto.getQuantity() - orderItem.getQuantity();
                            product.setQuantity(product.getQuantity() - diffQuantity);
                            productRepository.save(product);
                            orderItem.setQuantity(orderItemUpdateDto.getQuantity());
                            totalPrice -= product.getSellPrice() * orderItemUpdateDto.getQuantity();
                    }
                }else {
                    orderItems.remove(orderItem);
                }
                orderItemRepository.save(orderItem);
            }
        }



        order.setTotalPrice(totalPrice);
        return OrderDtoConverters.convertOrderToDto(order);
    }


    public List<OrderDto> getAllOrders() throws OrderNotFoundException {
        List<OrderDto> orderDtos = new ArrayList<>();
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()){
            throw new OrderNotFoundException("Order not found ");
        }
        for (Order order : orders) {
            OrderDto orderDto = OrderDtoConverters.convertOrderToDto(order);
            orderDtos.add(orderDto);
        }
        return orderDtos;
    }

    public List<OrderDto> getOrdersWithStatus(OrderStatus orderStatus) throws CustomerNotFoundExceptions {
        List<Order> customerOrders = orderRepository.findByOrderStatus(orderStatus);
        List<OrderDto> customerOrderDtos = OrderDtoConverters.convertOrdersToDos(customerOrders);
        return customerOrderDtos;
    }

    public List<OrderDto> getCustomerOrders(Integer customerId) throws CustomerNotFoundExceptions {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty())
            throw new CustomerNotFoundExceptions("Customer not found");

        List<Order> customerOrders = orderRepository.findAllByCustomerOrderByCreatedDateDesc(customerOptional.get());
        List<OrderDto> customerOrderDtos = OrderDtoConverters.convertOrdersToDos(customerOrders);
        return customerOrderDtos;
    }

    public OrderDto getById(Integer id) throws OrderNotFoundException {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()){
            throw new OrderNotFoundException("Order not found with id :" + id);
        }
        OrderDto orderDto =  OrderDtoConverters.convertOrderToDto(orderOptional.get());
        return orderDto;
    }

    public void deleteOrder(Integer id) {
        try {
            Optional<Order> orderOptional = orderRepository.findById(id);
            if (orderOptional.isEmpty()){
                throw new OrderNotFoundException("Order not found with id :" + id);
            }
            Order order = orderOptional.get();
            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem:orderItems){
                Product product = orderItem.getProduct();
                product.setQuantity(product.getQuantity() + orderItem.getQuantity());
                productRepository.save(product);
            }
            orderRepository.deleteById(id);
        }catch (Exception ex){
            throw new InternelServerException(ex);

        }
    }


    private boolean checkProductInventoryQuantity(Product product, Integer quantity) throws ProductNotFoundException {
        return quantity.compareTo(product.getQuantity()) < 0;
    }

}
