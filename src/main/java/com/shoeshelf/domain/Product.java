package com.shoeshelf.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product extends BaseEntity {

    private @NotNull String name;
    private @NotNull String imageURL;
    private @NotNull double buyPrice;
    private @NotNull double sellPrice;
    private @NotNull Integer quantity;
    private @NotNull String description;
    private boolean active;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    Category category;

}
