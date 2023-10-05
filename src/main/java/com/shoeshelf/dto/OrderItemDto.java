package com.shoeshelf.dto;


import com.shoeshelf.domain.Order;
import com.shoeshelf.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {


    private Integer id;


    private int quantity;

    private double price;


    private Date createdDate;

    private Order order;

    private Product product;
}
