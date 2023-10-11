package com.shoeshelf.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDto {

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
    @NotNull
    private boolean active;
}
