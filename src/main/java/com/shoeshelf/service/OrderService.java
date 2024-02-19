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
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {


    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public OrderDto createOrder(OrderCreateDto dto) throws CustomerNotFoundExceptions, ProductNotFoundException, ProductQuantityException {
        /* Gelen Obje Bos ise hata firlat*/
        if (dto == null)
            throw new NullPointerException();

        /* Dto objesindeki customerID ile custemer@i repostory den buluyorum */
        Optional<Customer> customerOptional = customerRepository.findById(dto.getCustomerId());

        /* Customer Yoksa Hata Firlat*/
        if (customerOptional.isEmpty())
            throw new CustomerNotFoundExceptions("Customer Not Found");

        /* DTO dan gelen ProductId bossa  Hata Firlat*/
        if (dto.getProductIds().isEmpty())
            throw new ProductNotFoundException("Products not found exception");

        Order order = new Order();
        order.setCustomer(customerOptional.get());
        order.setStatus(OrderStatus.Completed);
        /* Order Kayit icin hazir halde */
        orderRepository.save(order);
        double totalPrice = 0.0;
        List<OrderItemCreateDto> orderItemCreateDtos = dto.getProductIds();
        List<OrderItem> orderItems = new ArrayList<>();
        for (var orderItemCreateDto : orderItemCreateDtos){

            /* Order Productlar repository@den bulunur */
            Optional<Product> productOptional = productRepository.findById(orderItemCreateDto.getProductId());

            /* Product Yoksa Hata Firlat*/
            if(productOptional.isEmpty())
                throw new ProductNotFoundException("Products not found exception");

            Product product = productOptional.get();
            int quantity = orderItemCreateDto.getQuantity();
            /* Elimizdeki stok miktari bu methodda kontrol edilir */
            boolean checkProductInventoryQuantity = checkProductInventoryQuantity(product, quantity);

            /* Eger yeterli prodcut stock  Yoksa Hata Firlat*/
            if(!checkProductInventoryQuantity)
                throw new ProductQuantityException("Products not found exception");

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct_id(product.getId());
            orderItem.setProduct(product);
            orderItem.setPrice(product.getSellPrice());
            orderItem.setQuantity(quantity);
            orderItem.setOrder(order);
            /* Order Toplam tutar hesapliyorum */
            totalPrice += product.getSellPrice() * quantity;
            /* Siparis edilen urun adeti product uzerindeki stok miktarindan bu alanda cukarim yapilir */
            product.setQuantity(product.getQuantity()-quantity);
            productRepository.save(product);
            orderItemRepository.save(orderItem);
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        order.setTotalPrice(totalPrice);
        /* Frontend@e iletmek icin DTO convert ediyorum */
        return OrderDtoConverters.convertOrderToDto(order);
    }

     /*
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


      */

    public OrderDto update(OrderUpdateDto dto) throws OrderNotFoundException, CustomerNotFoundExceptions, ProductNotFoundException, ProductQuantityException {
        /* Order  buluyorum  order id bana dto ile frontend@den geliyor */
        Optional<Order> orderOptional = orderRepository.findById(dto.getId());

        /* Order Yoksa Hata Firlat */
        if (orderOptional.isEmpty()){
            throw new OrderNotFoundException("Order not found with id :" + dto.getId());
        }

        /* Customer bul*/
        Optional<Customer> customerOptional = customerRepository.findById(dto.getCustomerId());

        /* Customer Yoksa Hata Firlat */
        if (customerOptional.isEmpty())
            throw new CustomerNotFoundExceptions("Customer Not Found");

        /* OrderItems Yoksa Hata Firlat */
        if (dto.getOrderItemUpdateDtos().isEmpty())
            throw new ProductNotFoundException("Products not found exception");

        Order order = orderOptional.get();
        order.setModifiedDate(LocalDateTime.now());
        order.setCustomer(customerOptional.get());
        orderRepository.save(order);

        double totalPrice = 0.0;
        List<OrderItemUpdateDto> orderItemUpdateDtos = dto.getOrderItemUpdateDtos();
        List<OrderItem> orderItems = order.getOrderItems();
        Map<Integer, OrderItem> orderItemMap = new ConcurrentHashMap<>();
        for(OrderItem orderItem : orderItems) {
            orderItemMap.put(orderItem.getId(), orderItem);
        }

        for (OrderItemUpdateDto orderItemUpdateDto :orderItemUpdateDtos ) {
            OrderItem orderItem = orderItemMap.get(orderItemUpdateDto.getId());
            if (orderItem != null){
                Product product = productRepository.findById(orderItem.getProduct_id()).get();
                if (orderItemUpdateDto.getQuantity() > orderItem.getQuantity()){

                    try {
                        Integer diffQuantity = orderItemUpdateDto.getQuantity() - orderItem.getQuantity();
                        //Yetrli Quanatity varmi
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
                    Integer diffQuantity = orderItemUpdateDto.getQuantity() - orderItem.getQuantity();
                    product.setQuantity(product.getQuantity() - diffQuantity);
                    productRepository.save(product);
                    orderItem.setProduct(product);
                    orderItem.setQuantity(orderItemUpdateDto.getQuantity());
                    totalPrice -= product.getSellPrice() * orderItemUpdateDto.getQuantity();
                }

                orderItemRepository.save(orderItem);
                orderItemMap.remove(orderItem.getId());
            }
        }
        if(!orderItemMap.isEmpty())
            orderItemRepository.deleteAll(orderItemMap.values());

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
            for (OrderItem item:order.getOrderItems()){
                final Integer productId = item.getProduct_id();
                Product product = productRepository.findById(productId).get();
                item.setProduct(product);
            }
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
                Product product = productRepository.findById(orderItem.getProduct_id()).get();
                product.setQuantity(product.getQuantity() + orderItem.getQuantity());
                productRepository.save(product);
                orderItemRepository.deleteById(orderItem.getId());
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
