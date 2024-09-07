package com.example.shoes_ecommerce.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name = "slideshows")
public class Slideshows {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

}
