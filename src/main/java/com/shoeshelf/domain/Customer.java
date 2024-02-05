package com.shoeshelf.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shoeshelf.util.CustomerConverter;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Customer extends BaseEntity {


    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;
    @Column(name = "comments")
    private String comments;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Order> orders;



    public Customer(String firstName, String lastName, String email, String address, String comments) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.comments = comments;
    }

}
