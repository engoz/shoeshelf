package com.shoeshelf.dto.order;


import com.shoeshelf.dto.BaseDto;
import com.shoeshelf.dto.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto extends BaseDto {

    private Integer id;

    private int quantity;

    private double price;

    private Date createdDate;

    private Date modifiedDate;

    private Integer orderId;

    private ProductDto productDto;
}
