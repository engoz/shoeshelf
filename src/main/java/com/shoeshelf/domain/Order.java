package com.shoeshelf.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shoeshelf.status.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name="orders")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Order extends BaseEntity {


    @Column(name = "total_price")
    private double totalPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.Waiting;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

}
