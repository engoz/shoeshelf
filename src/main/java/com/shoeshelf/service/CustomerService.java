package com.shoeshelf.service;

import com.shoeshelf.domain.Customer;
import com.shoeshelf.dto.CustomerDto;
import com.shoeshelf.exceptions.CategoryNotFoundExceptions;
import com.shoeshelf.exceptions.CustomerNotFoundExceptions;
import com.shoeshelf.exceptions.InternelServerException;
import com.shoeshelf.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public List<CustomerDto> getAllCustomer() throws  CustomerNotFoundExceptions {
        List<CustomerDto> customerDtoList = new ArrayList<>();
        List<Customer> allCustomer = customerRepository.findAll();
        if (allCustomer.isEmpty()){
            throw new CustomerNotFoundExceptions("Customers is empty");
        }
        for (Customer customer:allCustomer) {
            CustomerDto customerDto =  convertDto(customer);
            customerDtoList.add(customerDto);
        }

        return customerDtoList;
    }


    public List<CustomerDto> getAllCustomerWithOrders() throws  CustomerNotFoundExceptions {
        List<CustomerDto> customerDtoList = new ArrayList<>();
        List<Customer> allCustomer = customerRepository.findAll();
        if (allCustomer.isEmpty()){
            throw new CustomerNotFoundExceptions("Customers is empty");
        }
        for (Customer customer:allCustomer) {
            CustomerDto customerDto =  convertDto(customer);
            customerDtoList.add(customerDto);
            //Order converts

        }

        return customerDtoList;
    }

    public CustomerDto getById(Integer id) throws CustomerNotFoundExceptions {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isEmpty()){
            throw new CustomerNotFoundExceptions("Customer not found with id :" + id);
        }
        CustomerDto customerDto =  convertDto(customerOptional.get());
        return customerDto;
    }



    public CustomerDto createCustomer(CustomerDto dto)  {
        if (dto == null)
            throw new NullPointerException();

        Customer customer = new Customer();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customerRepository.save(customer);
        dto.setId(customer.getId());
        return dto;
    }

    public CustomerDto update(CustomerDto dto) throws CustomerNotFoundExceptions{
        Optional<Customer> customerOptional = customerRepository.findById(dto.getId());
        if (customerOptional.isEmpty()){
            throw new CustomerNotFoundExceptions("Customer not found with id :" + dto.getId());
        }
        Customer customer = customerOptional.get();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customerRepository.save(customer);
        return dto;
    }

    public void deleteCustomer(Integer id) {
        try {
            customerRepository.deleteById(id);
        }catch(CategoryNotFoundExceptions ex){
            throw ex;
        }catch (Exception ex){
            throw new InternelServerException(ex);

        }
    }


    private CustomerDto convertDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setFirstName(customerDto.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setEmail(customer.getEmail());
        return customerDto;
    }
}
