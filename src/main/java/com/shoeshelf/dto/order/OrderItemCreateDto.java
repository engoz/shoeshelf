package com.shoeshelf.dto.order;

import com.shoeshelf.dto.BaseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemCreateDto extends BaseDto {

    @NotNull
    private Integer productId;

    @NotBlank
    private int quantity;

}
