package com.example.shoes_ecommerce.security;

import com.example.shoes_ecommerce.domain.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

    private Users users;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return users.getRoles();
    }
    @Override
    public String getPassword() {
        return users.getPassword();
    }
    @Override
    public String getUsername() {
        return users.getEmail();
    }

}