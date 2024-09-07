package com.example.shoes_ecommerce.feature.auth;

import com.example.shoes_ecommerce.domain.UserVerification;
import com.example.shoes_ecommerce.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserVerificationRepository extends JpaRepository<UserVerification, Long> {

        Optional<UserVerification> findByUsersAndVerifiedCode(Users users, String verifiedCode);

        Optional<UserVerification> findByUsers(Users users);
}
