package com.shoeshelf.dto;

import com.shoeshelf.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Integer id;

    private Date createdDate;

    private Double totalPrice;

    private List<OrderItemDto> orderItems;

    private Customer customer;
}
