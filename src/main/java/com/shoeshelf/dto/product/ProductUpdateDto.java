package com.shoeshelf.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateDto {
    @NotNull
    private Integer id;
    @NotNull
    private String name;
    @NotBlank
    private  String imageURL;
    @NotNull
    private double buyPrice;
    @NotNull
    private double sellPrice;
    @NotNull
    private Integer quantity;
    @NotBlank
    private String description;
    @NotNull
    private Integer categoryId;
}
