package com.shoeshelf.util;

import com.shoeshelf.domain.Order;
import com.shoeshelf.domain.OrderItem;
import com.shoeshelf.dto.order.OrderDto;
import com.shoeshelf.dto.order.OrderItemDto;

import java.util.ArrayList;
import java.util.List;

public class OrderDtoConverters {

    public static OrderDto convertOrderToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setCreatedDate(order.getCreatedDate());
        orderDto.setModifiedDate(order.getModifiedDate());
        orderDto.setCustomer(order.getCustomer());
        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setOrderItems(OrderDtoConverters.convertOrderItemsDto(order.getOrderItems()));
        return orderDto;
    }

    public static List<OrderItem> convertOrderItems(List<OrderItemDto> orderItems){
        return new ArrayList<>();
    }
    public static  List<OrderItemDto>  convertOrderItemsDto(List<OrderItem> orderItems){
        List<OrderItemDto> orderItemDtos = new ArrayList<>();
        for (OrderItem orderItem : orderItems){
            OrderItemDto orderItemDto = new OrderItemDto();
            orderItemDto.setId(orderItem.getId());
            orderItemDto.setPrice(orderItem.getPrice());
            orderItemDto.setQuantity(orderItem.getQuantity());
            orderItemDto.setOrderId(orderItem.getOrder().getId());
            orderItemDto.setProductDto(ProductDtoConverters.convertProductToDto(orderItem.getProduct()));
        }
        return new ArrayList<>();
    }
}
