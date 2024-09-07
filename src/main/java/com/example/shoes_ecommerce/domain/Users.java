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
@Entity(name = "users")
@Table
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column( nullable = false,length = 100)
    private String firstName;

    @Column( nullable = false,length = 100)
    private String lastName;

    @Column( nullable = false,length = 100)
    private String email;

    @Column(nullable = false,length = 100)
    private String password;

    @Column( nullable = false,length = 100, unique = true)
    private String phoneNumber;

    @Column( nullable = false,length = 100)
    private String address;

    @Column( nullable = false,length = 100)
    private String gender;

    private String profile;

    private String uuid;

    private Boolean isDeleted;

    private Boolean isBlocked;

    private Boolean isVerified;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "users_id",referencedColumnName ="id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id",referencedColumnName ="id")
    )
    private List<Roles> roles;

    @OneToMany(mappedBy = "users")
    private List<Bookmarks> bookmarks;



}
