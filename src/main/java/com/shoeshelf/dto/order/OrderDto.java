package com.shoeshelf.dto.order;

import com.shoeshelf.dto.BaseDto;
import com.shoeshelf.dto.customer.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto extends BaseDto {

    private Integer id;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    private Double totalPrice;

    private List<OrderItemDto> orderItems;

    private CustomerDto customerDto;
}
