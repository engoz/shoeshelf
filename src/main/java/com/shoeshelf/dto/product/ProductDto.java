package com.shoeshelf.dto.product;


import com.shoeshelf.dto.BaseDto;
import com.shoeshelf.dto.category.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto extends BaseDto {
    private Integer id;
    private String name;
    private  String imageURL;
    private double buyPrice;
    private double sellPrice;
    private String description;
    private Integer quantity;
    private CategoryDto categoryDto;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private boolean active;
}

