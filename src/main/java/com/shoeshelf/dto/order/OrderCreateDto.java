package com.shoeshelf.dto.order;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateDto {

    @NotEmpty
    private Integer customerId;

    @NotEmpty
    private List<OrderItemCreateDto> productIds;
}
