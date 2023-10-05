package com.shoeshelf.dto;


import com.shoeshelf.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {


    private Integer id;

    private String name;
    private  String imageURL;
    private double price;
    private String description;

    Category category;
}
