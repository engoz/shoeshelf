package com.shoeshelf.dto.order;

import com.shoeshelf.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemUpdateDto extends BaseDto {

    private Integer id;

    private int quantity;

    private Integer orderId;

    private Integer productId;
}
