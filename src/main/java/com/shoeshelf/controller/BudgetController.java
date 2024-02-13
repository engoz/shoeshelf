package com.shoeshelf.controller;


import com.shoeshelf.domain.Customer;
import com.shoeshelf.dto.budget.BudgetDto;
import com.shoeshelf.dto.budget.CustomerBudgetDto;
import com.shoeshelf.dto.budget.ProductBudgetDto;
import com.shoeshelf.dto.category.CategoryDto;
import com.shoeshelf.dto.customer.CustomerDto;
import com.shoeshelf.dto.product.ProductCreateDto;
import com.shoeshelf.dto.product.ProductDto;
import com.shoeshelf.dto.product.ProductUpdateDto;
import com.shoeshelf.exceptions.CategoryNotFoundExceptions;
import com.shoeshelf.exceptions.ProductNotFoundException;
import com.shoeshelf.service.BudgetService;
import com.shoeshelf.service.CustomerService;
import com.shoeshelf.service.OrderService;
import com.shoeshelf.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/budget")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
public class BudgetController {

    private final BudgetService budgetService;

    // All
    @GetMapping("/all")
    public ResponseEntity<BudgetDto> getAllBudget(){
        BudgetDto budgetDto = budgetService.getAllBudget();
        return ResponseEntity.ok(budgetDto);
    }

    @GetMapping("/all/{from}/{to}")
    public ResponseEntity<BudgetDto> getAllBudgetFromDate(@PathVariable Date from, Date to){
        BudgetDto budgetDto = budgetService.getAllBudget(from, to);
        return ResponseEntity.ok(budgetDto);
    }

    //Customer
    @GetMapping("/revenuecustomer/{customerId}")
    public ResponseEntity<CustomerBudgetDto> getCustomerBudget(@PathVariable Integer customerId){
        CustomerBudgetDto customerBudget = budgetService.getCustomerBudget(customerId);
        return ResponseEntity.ok(customerBudget);
    }

    @GetMapping("/revenuecustomer/{customerId}/{from}/{to}")
    public ResponseEntity<CustomerBudgetDto> getCustomerBudgetFromDate(@PathVariable Integer customerId, @PathVariable Date from, Date to){
        CustomerBudgetDto customerBudget = budgetService.getCustomerBudget(customerId, from, to);
        return ResponseEntity.ok(customerBudget);
    }

    //Product
    @GetMapping("/revenueproduct/{productId}")
    public ResponseEntity<ProductBudgetDto> getProdutcBudget(@PathVariable Integer productId){
        ProductBudgetDto productBudget = budgetService.getProductBudget(productId);
        return ResponseEntity.ok(productBudget);
    }

    @GetMapping("/revenueproduct/{productId}/{from}/{to}")
    public ResponseEntity<ProductBudgetDto> getProductBudgetFromDate(@PathVariable Integer productId,@PathVariable Date from, Date to){
        ProductBudgetDto productBudget = budgetService.getProductBudget(productId, from, to);
        return ResponseEntity.ok(productBudget);
    }


}
