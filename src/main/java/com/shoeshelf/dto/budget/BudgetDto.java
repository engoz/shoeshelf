package com.shoeshelf.dto.budget;

import com.shoeshelf.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDto extends BaseDto {
    private double revenue;
    private double profit;
    private double expenses;
    private double quantityOfSales;
}
