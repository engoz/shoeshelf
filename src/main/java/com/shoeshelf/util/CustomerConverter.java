package com.shoeshelf.util;

import com.shoeshelf.domain.Customer;
import com.shoeshelf.dto.customer.CustomerDto;

public class CustomerConverter extends Converter<CustomerDto, Customer> {

    public CustomerConverter(){
        super(CustomerConverter::convertToEntity, CustomerConverter::convertToDto);
    }

    private static CustomerDto convertToDto(Customer customer) {
        return new CustomerDto(customer.getId(),customer.getFirstName(),customer.getLastName(),customer.getEmail(),customer.getAddress(),customer.getComments());
    }

    private static Customer convertToEntity(CustomerDto dto) {
        return new Customer(dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getAddress(), dto.getComments());
    }

}
