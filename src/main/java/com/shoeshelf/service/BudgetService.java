package com.shoeshelf.service;

import com.shoeshelf.domain.Order;
import com.shoeshelf.domain.OrderItem;
import com.shoeshelf.domain.Product;
import com.shoeshelf.dto.budget.BudgetDto;
import com.shoeshelf.dto.budget.CustomerBudgetDto;
import com.shoeshelf.dto.budget.ProductBudgetDto;
import com.shoeshelf.dto.order.OrderDto;
import com.shoeshelf.repository.CustomerRepository;
import com.shoeshelf.repository.OrderItemRepository;
import com.shoeshelf.repository.OrderRepository;
import com.shoeshelf.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class BudgetService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CustomerRepository customerRepository;

    public double getTotalExpenses(){
        double totalExpenses = 0;
        List<Product> prodcuts = productRepository.findAll();
        if(prodcuts.isEmpty()){
            return totalExpenses;
        }
        for (Product product : prodcuts) {
        double expenses = product.getQuantity() * product.getBuyPrice();
        totalExpenses += expenses;
        }
        return totalExpenses;
    }

    public Map<String, Double> getTotalOrderRevenue(){
        Map<String, Double> orderMap = new HashMap<>();
        orderMap.put("quantity", Double.NaN);
        orderMap.put("revenue", Double.NaN);
        double totalRevenue = 0;
        double totalQuantitiy = 0;
        List<Order> orders = orderRepository.findAll();
        if(orders.isEmpty()){
            return orderMap;
        }
        for (Order order:orders) {
            double revenue = order.getTotalPrice();
            totalRevenue += revenue;
            for (OrderItem orderItem : order.getOrderItems()) {
             double quantitiy =  orderItem.getQuantity();
             totalQuantitiy += quantitiy;
            }

        }

        orderMap.put("quantity", totalQuantitiy);
        orderMap.put("revenue", totalRevenue);
        return orderMap;
    }

    public BudgetDto getAllBudget() {
        final Map<String, Double> map = getTotalOrderRevenue();
        BudgetDto dto = new BudgetDto();
        double totalRevenue = map.get("revenue");
        double quantity = map.get("quantity");
        double totalExpenses =getTotalExpenses();
        double profit = totalRevenue - totalExpenses;
        dto.setExpenses(totalExpenses);
        dto.setRevenue(totalRevenue);
        dto.setProfit(profit);
        dto.setQuantityOfSales(quantity);
        return dto;
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
