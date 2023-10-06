package com.shoeshelf.controller;


import com.shoeshelf.dto.order.OrderCreateDto;
import com.shoeshelf.dto.order.OrderDto;
import com.shoeshelf.dto.order.OrderUpdateDto;
import com.shoeshelf.exceptions.CustomerNotFoundExceptions;
import com.shoeshelf.exceptions.OrderNotFoundExceptions;
import com.shoeshelf.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;

    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> getAll(){
        try {
            List<OrderDto> allOrders = orderService.getAllOrders();
            return ResponseEntity.ok(allOrders);
        }catch (OrderNotFoundExceptions e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping("/getById/{id}")
    public ResponseEntity<OrderDto> getById(@PathVariable String id){
        try {
            OrderDto orderDto = orderService.getById(Integer.valueOf(id));
            return ResponseEntity.ok(orderDto);
        }catch (OrderNotFoundExceptions e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<OrderDto> create(@RequestBody OrderCreateDto dto){
        try {
            OrderDto orderDto = orderService.createOrder(dto);
            return ResponseEntity.ok(orderDto);
        }catch (Exception | CustomerNotFoundExceptions ex){
            return ResponseEntity.internalServerError().build();
        }
    }


    @PutMapping("/update")
    public ResponseEntity<OrderDto> update(@RequestBody OrderUpdateDto dto){
        try {
            OrderDto orderDto = orderService.update(dto);
            return ResponseEntity.ok(orderDto);
        }catch (OrderNotFoundExceptions e){
            return ResponseEntity.notFound().build();
        }catch (Exception ex){
            return ResponseEntity.internalServerError().build();
        }
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex){
            return ResponseEntity.internalServerError().build();
        }
    }
}
