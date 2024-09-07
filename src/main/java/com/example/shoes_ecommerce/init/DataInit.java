package com.example.shoes_ecommerce.init;

import com.example.shoes_ecommerce.domain.Roles;
import com.example.shoes_ecommerce.domain.Users;
import com.example.shoes_ecommerce.feature.user.RolesRepository;
import com.example.shoes_ecommerce.feature.user.UsersRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final RolesRepository rolesRepository;

    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    void init() {

        if(usersRepository.count() == 0) {

            Roles user = new Roles();
            user.setName("USER");

            Roles customer = new Roles();
            customer.setName("CUSTOMER");

            Roles supplier = new Roles();
            supplier.setName("SUPPLIER");

            Roles admin = new Roles();
            admin.setName("ADMIN");

            rolesRepository.saveAll(List.of(user,customer,supplier,admin));

            Users users1 = new Users();
            users1.setUuid(UUID.randomUUID().toString());
            users1.setEmail("Sanneasa@gmail.com");
            users1.setPassword(passwordEncoder.encode("qwqt"));
            users1.setFirstName("San");
            users1.setLastName("Neasa");
            users1.setPhoneNumber("09765241888");
            users1.setProfile("1.jpg");
            users1.setGender("Male");
            users1.setAddress("Phnom Phenh");
            users1.setIsDeleted(false);
            users1.setIsVerified(true);
            users1.setIsBlocked(false);
            users1.setRoles(List.of(user,admin));


            Users users2 = new Users();
            users2.setUuid(UUID.randomUUID().toString());
            users2.setEmail("Maly@gmail.com");
            users2.setPassword(passwordEncoder.encode("qwqt"));
            users2.setFirstName("Kim");
            users2.setLastName("Maly");
            users2.setPhoneNumber("09765241999");
            users2.setProfile("2.jpg");
            users2.setGender("Female");
            users2.setIsDeleted(false);
            users2.setIsVerified(true);
            users2.setIsBlocked(false);
            users2.setAddress("Banteaymeancheay");
            users2.setRoles(List.of(user,supplier));

            Users users3 = new Users();
            users3.setUuid(UUID.randomUUID().toString());
            users3.setEmail("Lyzai@gmail.com");
            users3.setPassword(passwordEncoder.encode("qwqt"));
            users3.setFirstName("Na");
            users3.setLastName("Lyzai");
            users3.setIsDeleted(false);
            users3.setIsVerified(true);
            users3.setIsBlocked(false);
            users3.setPhoneNumber("09765241777");
            users3.setProfile("3.jpg");
            users3.setGender("Female");
            users3.setAddress("Tbong khmom");
            users3.setRoles(List.of(user,customer));
            usersRepository.saveAll(List.of(users1,users2,users3));

        }

    }
}
