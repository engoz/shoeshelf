package com.shoeshelf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Integer id;


    private  String categoryName;

    private String description;

    private String imageUrl;
}
