package com.example.shoes_ecommerce.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "shopInfos")
@Table
public class ShopInfos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column( nullable = false,length = 100)
    private String name;

    @Column( nullable = false,length = 100,unique = true)
    private String email;

    @Column( nullable = false,length = 100,unique = true)
    private String phone;

    private String logo;

    private String address;

    private String uuid;

}
