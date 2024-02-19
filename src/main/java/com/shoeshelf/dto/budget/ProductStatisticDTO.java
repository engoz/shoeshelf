package com.shoeshelf.dto.budget;

import com.shoeshelf.dto.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductStatisticDTO {

    /* Satis rakamlarini fronend ile haberkestiren nesne sinifi */

    private double totalProducts;
    private double todaySell;
    private double weekSell;
    private double monthSell;
    private double yearSell;
    private double TotalSell;
    private double stocks;

}
