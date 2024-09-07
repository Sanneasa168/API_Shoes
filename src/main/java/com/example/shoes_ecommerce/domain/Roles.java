package com.example.shoes_ecommerce.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "roles")
@Table
public class Roles implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // USER,CUSTOMERS , SUPPLIERS , ADMIN
    @Column( length = 50)
    private String  name;

    private String uuid;

    @ManyToMany(mappedBy = "roles")
    private List<Users> users;

    @Override
    public String getAuthority() {
        return   name ;
    }
}
