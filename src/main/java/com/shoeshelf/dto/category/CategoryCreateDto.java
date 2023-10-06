package com.shoeshelf.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreateDto {

    @NotBlank
    private  String categoryName;
    @NotBlank
    private String description;
    @NotBlank
    private String imageUrl;

}
