package com.example.shoes_ecommerce.feature.user;

import com.example.shoes_ecommerce.domain.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface RolesRepository extends JpaRepository<Roles, Integer> {

    @Query(
            """
              SELECT r
              FROM roles r
              WHERE r.name = 'USER'
            """
    )
    List<Roles> findRolesByUser();

    @Query(
            """
            SELECT r
            FROM roles r
            WHERE r.name = 'CUSTOMER'
            """
    )
    List<Roles> findRolesByCustomer();

}
