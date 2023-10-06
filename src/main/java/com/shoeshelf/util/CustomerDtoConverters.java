package com.shoeshelf.util;

import com.shoeshelf.domain.Customer;
import com.shoeshelf.dto.customer.CustomerCreateDto;
import com.shoeshelf.dto.customer.CustomerDto;

public class CustomerDtoConverters {

    public static CustomerDto convertCustomerToDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setFirstName(customerDto.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setAddress(customer.getAddress());
        customerDto.setComments(customer.getComments());
        return customerDto;
    }

    public static Customer convertDtoToCustomer(CustomerCreateDto dto) {
        Customer customer = new Customer();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setAddress(dto.getAddress());
        customer.setComments(dto.getComments());
        return customer;
    }

    public static Customer convertDtoToCustomer(CustomerDto dto) {
        Customer customer = new Customer();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setAddress(dto.getAddress());
        customer.setComments(dto.getComments());
        return customer;
    }

}
