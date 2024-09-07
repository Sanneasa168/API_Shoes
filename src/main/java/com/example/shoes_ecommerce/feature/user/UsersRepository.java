package com.example.shoes_ecommerce.feature.user;

import com.example.shoes_ecommerce.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UsersRepository extends JpaRepository<Users,Integer> {

      // SELECT EXISTS(SELECT *FROM  users WHERE email
      Boolean existsByEmail(String email);

      //SELECT EXISTS(SELECT *FROM user WERE phoneNumber
      Boolean existsByPhoneNumber(String phoneNumber);

      //SELECT EXISTS(SELECT *FROM user WERE phoneNumber

      // SELECT *FORM user WHERE uuid
      Optional<Users> findByUuid(String uuid);

      // SELECT *FORM email WHERE  email

     // SELECT *FORM email WHERE  email
      Optional<Users> findByEmail(String email);

}
