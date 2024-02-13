package com.shoeshelf.controller;

import com.shoeshelf.dto.customer.CustomerCreateDto;
import com.shoeshelf.dto.customer.CustomerDto;
import com.shoeshelf.dto.customer.CustomerUpdateDto;
import com.shoeshelf.exceptions.CustomerNotFoundExceptions;
import com.shoeshelf.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/all")
    public ResponseEntity<List<CustomerDto>> getAll(){
        try {
            List<CustomerDto> allCustomer = customerService.getAllCustomer();
            return ResponseEntity.ok(allCustomer);
        }catch (CustomerNotFoundExceptions e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/allWithOrders")
    public ResponseEntity<List<CustomerDto>> getAllWithOrders(){
        try {
            List<CustomerDto> allCustomer = customerService.getAllCustomerWithOrders();
            return ResponseEntity.ok(allCustomer);
        }catch (CustomerNotFoundExceptions e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping("/getById/{id}")
    public ResponseEntity<CustomerDto> getById(@PathVariable String id){
        try {
            CustomerDto customerDto = customerService.getById(Integer.valueOf(id));
            return ResponseEntity.ok(customerDto);
        }catch (CustomerNotFoundExceptions e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/getByFirstName/{name}")
    public ResponseEntity<CustomerDto> getByFirstName(@PathVariable String name){
        try {
            CustomerDto customerDto = customerService.getByFirstName(name);
            return ResponseEntity.ok(customerDto);
        }catch (CustomerNotFoundExceptions e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/getByLastName{name}")
    public ResponseEntity<CustomerDto> getByLastName(@PathVariable String name){
        try {
            CustomerDto customerDto = customerService.getByLastName(name);
            return ResponseEntity.ok(customerDto);
        }catch (CustomerNotFoundExceptions e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<CustomerDto> create(@RequestBody CustomerCreateDto dto){
        try {
            CustomerDto customerDto = customerService.createCustomer(dto);
            return ResponseEntity.ok(customerDto);
        }catch (Exception ex){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<CustomerDto> update(@RequestBody CustomerUpdateDto dto){
        try {
            CustomerDto customerDto = customerService.update(dto);
            return ResponseEntity.ok(customerDto);
        }catch (CustomerNotFoundExceptions e){
            return ResponseEntity.notFound().build();
        }catch (Exception ex){
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.ok().build();
        }catch (Exception ex){
            return ResponseEntity.internalServerError().build();
        }
    }
}
