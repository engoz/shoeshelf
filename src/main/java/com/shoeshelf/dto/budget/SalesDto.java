package com.shoeshelf.dto.budget;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesDto {

        private double salesProfit;
        private double salesRevenue;
        private double salesExpenses;

}
