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
@Entity
@Table(name = "bookmarks")
public class Bookmarks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String uuid;

    @Column(nullable = false)
    private Boolean isBookmark;

    @OneToOne
    private  Products products;

    @ManyToOne
   private  Users users;

}
