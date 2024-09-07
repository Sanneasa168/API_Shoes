package com.example.shoes_ecommerce.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "products")
@Table
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer discount;

    @Column(nullable = false)
    private  Integer  stock;

    @Column(nullable = false)
    private Integer unitPrice;

    private Boolean status;

    private String uuid;

    @OneToOne
    private  Warranties warranties;

    @ManyToOne
    private Categories categories;

    @ManyToMany(mappedBy ="products")
    private List<Orders> orders;
}
