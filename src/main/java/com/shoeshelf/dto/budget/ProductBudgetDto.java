package com.shoeshelf.dto.budget;

import com.shoeshelf.dto.BaseDto;
import com.shoeshelf.dto.customer.CustomerDto;
import com.shoeshelf.dto.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductBudgetDto extends BaseDto {

    private ProductDto productDto;
    private double revenue;
    private double profit;
    private double expenses;
    private double quantityOfSales;
}
