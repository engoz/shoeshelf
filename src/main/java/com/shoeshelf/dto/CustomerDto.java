package com.shoeshelf.dto;


import com.shoeshelf.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private Integer id;

    private String firstName;


    private String lastName;


    private String email;

    private List<Order> orders;
}
