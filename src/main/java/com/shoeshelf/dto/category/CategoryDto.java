package com.shoeshelf.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Integer id;

    private  String categoryName;

    private String description;

    private String imageUrl;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
