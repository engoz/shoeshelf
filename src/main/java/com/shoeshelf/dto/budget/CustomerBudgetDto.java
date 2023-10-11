package com.shoeshelf.dto.budget;

import com.shoeshelf.dto.customer.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerBudgetDto {

    private CustomerDto customerDto;
    private double revenue;
    private double profit;
    private double expenses;
    private double quantityOfSales;
}
