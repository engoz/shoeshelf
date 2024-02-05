package com.shoeshelf.dto.order;

import com.shoeshelf.dto.BaseDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateDto extends BaseDto {

    @NotEmpty
    private Integer customerId;

    @NotEmpty
    private List<OrderItemCreateDto> productIds;
}
