package com.example.shoes_ecommerce.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "payments")
public class Payments {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,length = 50)
    private double amount;

    @Column(nullable = false,length = 50)
    private String paymentStatus;// e.g., "PENDING", "COMPLETED", "FAILED"

    @Column(nullable = false,length = 50)
    private LocalDateTime paymentDate;

    private String uuid;

    @OneToOne
    private Orders order;

}
