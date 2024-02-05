package com.shoeshelf.dto.category;

import com.shoeshelf.dto.BaseDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreateDto extends BaseDto {

    @NotBlank
    private  String categoryName;
    @NotBlank
    private String description;
    @NotBlank
    private String imageUrl;

}
