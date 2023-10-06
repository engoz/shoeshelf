package com.shoeshelf.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemUpdateDto {

    private Integer id;

    private int quantity;

    private Integer orderId;

    private Integer productId;
}
