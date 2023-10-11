package com.shoeshelf.dto.budget;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDto {
    private double revenue;
    private double profit;
    private double expenses;
    private double quantityOfSales;
}
