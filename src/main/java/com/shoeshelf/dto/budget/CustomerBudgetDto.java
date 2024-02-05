package com.shoeshelf.dto.budget;

import com.shoeshelf.dto.BaseDto;
import com.shoeshelf.dto.customer.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerBudgetDto extends BaseDto {

    private CustomerDto customerDto;
    private double revenue;
    private double profit;
    private double expenses;
    private double quantityOfSales;
}
