package com.example.shoes_ecommerce.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = " warranties")
@Table
public class Warranties {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDate warrantiesDate;

    @Column(nullable = false)
    private LocalTime warrantiesTime;

    private String uuid;

    @OneToOne
    private    Products products;

}
