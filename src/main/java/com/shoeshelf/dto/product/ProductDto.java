package com.shoeshelf.dto.product;


import com.shoeshelf.dto.category.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {


    private Integer id;

    private String name;
    private  String imageURL;
    private double price;
    private String description;

    private Integer quantity;

    private CategoryDto categoryDto;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}

