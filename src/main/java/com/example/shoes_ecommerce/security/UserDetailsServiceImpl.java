package com.example.shoes_ecommerce.security;

import com.example.shoes_ecommerce.domain.Users;
import com.example.shoes_ecommerce.feature.user.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User has not been found"));

        log.info("User: {}", users.getEmail());
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUsers(users);
        return customUserDetails;

    }

}