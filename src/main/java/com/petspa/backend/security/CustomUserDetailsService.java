package com.petspa.backend.security;

import com.petspa.backend.entity.Account;
import com.petspa.backend.repository.AccountRepository;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + email));

        // Convert Account entity to UserDetails
        return new org.springframework.security.core.userdetails.User(
                account.getEmail(),
                account.getPassword(),
                new ArrayList<>() // You can add roles and authorities here
        );
    }
}
