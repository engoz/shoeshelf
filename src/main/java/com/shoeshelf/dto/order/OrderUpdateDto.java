package com.shoeshelf.dto.order;

import com.shoeshelf.status.OrderStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateDto {
    @NotEmpty
    private Integer id;
    @NotEmpty
    private Integer customerId;
    @NotEmpty
    private OrderStatus status;
    @NotEmpty
    private List<OrderItemUpdateDto> orderItemUpdateDtos;
}
