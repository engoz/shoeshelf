package com.shoeshelf.service;

import com.shoeshelf.dto.budget.BudgetDto;
import com.shoeshelf.dto.budget.CustomerBudgetDto;
import com.shoeshelf.dto.budget.ProductBudgetDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class BudgetService {


    public BudgetDto getAllBudget() {
        return null;
    }

    public BudgetDto getAllBudget(Date from, Date to) {
        return null;
    }

    public CustomerBudgetDto getCustomerBudget(Integer customerId) {
        return null;
    }

    public CustomerBudgetDto getCustomerBudget(Integer customerId, Date from, Date to) {
        return null;
    }

    public ProductBudgetDto getProductBudget(Integer productId) {
        return null;
    }

    public ProductBudgetDto getProductBudget(Integer productId, Date from, Date to) {
        return null;
    }
}
