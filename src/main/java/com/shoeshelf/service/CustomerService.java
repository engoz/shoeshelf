package com.shoeshelf.service;

import com.shoeshelf.domain.Customer;
import com.shoeshelf.dto.customer.CustomerCreateDto;
import com.shoeshelf.dto.customer.CustomerDto;
import com.shoeshelf.dto.customer.CustomerUpdateDto;
import com.shoeshelf.exceptions.*;
import com.shoeshelf.repository.CustomerRepository;
import com.shoeshelf.util.CustomerDtoConverters;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.shoeshelf.util.CustomerDtoConverters.convertCustomerToDto;
import static com.shoeshelf.util.CustomerDtoConverters.convertDtoToCustomer;

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
            CustomerDto customerDto =  CustomerDtoConverters.convertCustomerToDto(customer);
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
            CustomerDto customerDto =  CustomerDtoConverters.convertCustomerToDto(customer);
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
        CustomerDto customerDto =  CustomerDtoConverters.convertCustomerToDto(customerOptional.get());
        return customerDto;
    }



    public CustomerDto createCustomer(CustomerCreateDto dto)  {
        if (dto == null)
            throw new NullPointerException();

        Customer byEmail = customerRepository.findByEmail(dto.getEmail());
        if (byEmail != null)
            throw new CustomerExistException("Customer Exist");

        Customer customer = convertDtoToCustomer(dto);
        customerRepository.save(customer);
        CustomerDto customerDto = convertCustomerToDto(customer);
        return customerDto;
    }

    public CustomerDto update(CustomerUpdateDto dto) throws CustomerNotFoundExceptions{
        Optional<Customer> customerOptional = customerRepository.findById(dto.getId());
        if (customerOptional.isEmpty()){
            throw new CustomerNotFoundExceptions("Customer not found with id :" + dto.getId());
        }
        Customer customer = customerOptional.get();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setModifiedDate(LocalDateTime.now());
        customer.setAddress(dto.getAddress());
        customer.setComments(dto.getComments());
        customerRepository.save(customer);
        CustomerDto customerDto = convertCustomerToDto(customer);
        return customerDto;
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



}
