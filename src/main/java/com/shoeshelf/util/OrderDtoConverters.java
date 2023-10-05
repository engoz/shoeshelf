package com.shoeshelf.util;

import com.shoeshelf.domain.OrderItem;
import com.shoeshelf.dto.OrderItemDto;

import java.util.ArrayList;
import java.util.List;

public class OrderDtoConverters {

    public static List<OrderItem> convertOrderItems(List<OrderItemDto> orderItems){
        return new ArrayList<>();
    }
    public static  List<OrderItemDto>  convertOrderItemsDto(List<OrderItem> orderItems){
        return new ArrayList<>();
    }
}
