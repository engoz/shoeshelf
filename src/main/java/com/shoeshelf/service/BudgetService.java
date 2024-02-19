package com.shoeshelf.service;

import com.shoeshelf.domain.Order;
import com.shoeshelf.domain.OrderItem;
import com.shoeshelf.domain.Product;
import com.shoeshelf.dto.budget.*;
import com.shoeshelf.dto.order.OrderDto;
import com.shoeshelf.repository.CustomerRepository;
import com.shoeshelf.repository.OrderItemRepository;
import com.shoeshelf.repository.OrderRepository;
import com.shoeshelf.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class BudgetService {

    Logger logger = LoggerFactory.getLogger(BudgetService.class);
    private static final DateTimeFormatter DATE_PARSER
            = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CustomerRepository customerRepository;

    /* Budget Sales Analys kismi  burda yapiyorum */
    public SalesDto getSalesAnalysis(){
        double revenue = 0;
        double profit = 0;
        double expenses = 0;
        List<Order> orders = orderRepository.findAll();
        SalesDto salesDto = new SalesDto();
        if(orders.isEmpty()){
            return  salesDto;
        }

        /* Tum Budget Analiz hesaplari bu alanda yapilir product uzerinde kalan stok ve alis fiyati carpimi ile hesaplanir. */
        for (Order order:orders) {
            final double rev = order.getTotalPrice();
            revenue += rev;
            double orderExpenses = 0;
            for (OrderItem orderItem : order.getOrderItems()) {
                double quantitiy = orderItem.getQuantity();
                Integer product_id = orderItem.getProduct_id();
                Product product = productRepository.findById(product_id).get();
                final double ex = product.getBuyPrice() * quantitiy;
                orderExpenses += ex;
            }
            expenses +=  orderExpenses;
            final double orderPrf = rev - orderExpenses;
            profit += orderPrf;
        }
        logger.info(new StringBuilder().append("Profit, Revenue, Expenses = ").append(profit).append(" ").append(revenue).append(" ").append(expenses).toString());
        salesDto.setSalesProfit(profit);
        salesDto.setSalesRevenue(revenue);
        salesDto.setSalesExpenses(expenses);

        return salesDto;

    }
    /* Satislarin total olarak hesaplandigi metod */
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
    /* Toplam Satislarin Revenue hesaplandi alan */
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

    /* Inventory icindeki Overview uzerundeki Satis istartistiklerinin hesaplandigi metod */
    public ProductStatisticDTO getProductStatistic() {
        double stock = 0;
        double totalProducts = 0;
        double totalSell = 0;
        double todaySell = 0;
        double weekSell = 0;
        double monthSell = 0;
        double yearSell = 0;

        /* Tum productlar uzerinde kalan stok miktari toplanarak total product hesaplanir */
        for (Product product : productRepository.findAll()) {
            stock += product.getQuantity();
            totalProducts +=1;
        }

        ProductStatisticDTO dto = new ProductStatisticDTO();
        dto.setStocks(stock);
        dto.setTotalProducts(totalProducts);

        ZoneId zone = ZoneId.systemDefault();
        LocalDate today = LocalDate.now(zone);


        for (OrderItem orderItem : orderItemRepository.findAll()) {
            totalSell += orderItem.getQuantity();
            final int year = orderItem.getCreatedDate().getYear();
            final int month = orderItem.getCreatedDate().getMonth().getValue();
            final int week = orderItem.getCreatedDate().get(ChronoField.ALIGNED_WEEK_OF_YEAR);
            final int day = orderItem.getCreatedDate().get(ChronoField.DAY_OF_YEAR);

            /* Haftalik order adetlerinin hesaplandigi alan*/
            if (getCurrentYear() == year && getCurrentWeek() == week) {
                weekSell += orderItem.getQuantity();
                System.out.println(weekSell + " is in the week.");
            }

            /* Aylik order adetlerinin hesaplandigi alan*/
            if (getCurrentYear() == year && getCurrentMonth() == month) {
                monthSell += orderItem.getQuantity();
                System.out.println(monthSell + " is in the Month.");
            }

            /* Gunluk order adetlerinin hesaplandigi alan*/
            if (getCurrentYear() == year && getCurrentDay() == day) {
                todaySell += orderItem.getQuantity();
                System.out.println(yearSell + " is in the Year.");
            }

            /* Yillik order adetlerinin hesaplandigi alan*/
            if (getCurrentYear() == year) {
                yearSell += orderItem.getQuantity();
                System.out.println(yearSell + " is in the Year.");
            }
        }

        dto.setTotalSell(totalSell);
        dto.setWeekSell(weekSell);
        dto.setMonthSell(monthSell);
        dto.setYearSell(yearSell);
        dto.setTodaySell(todaySell);


        return dto;

    }

    private static int getCurrentDay() {
        LocalDateTime now = LocalDateTime.now();
        final int day = now.get(ChronoField.DAY_OF_YEAR);
        return day;
    }
    private static int getCurrentWeek() {
        LocalDate date = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return date.get(weekFields.weekOfWeekBasedYear());
    }

    private static int getCurrentMonth() {
        LocalDate date = LocalDate.now();
        YearMonth yearMonth = YearMonth.now();
        return yearMonth.getMonth().getValue();
    }

    private static int getCurrentYear() {
        LocalDate date = LocalDate.now();
        YearMonth yearMonth = YearMonth.now();
        return yearMonth.getYear();
    }
}
