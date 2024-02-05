package com.shoeshelf.domain;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Category extends BaseEntity {

    private @NotNull String categoryName;

    private @NotNull String description;

    private @NotNull String imageUrl;

    // add imageURL here

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    Set<Product> products;

}
