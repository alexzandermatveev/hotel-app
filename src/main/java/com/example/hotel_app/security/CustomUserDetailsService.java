package com.example.hotel_app.security;

import com.example.hotel_app.model.AppUser;
import com.example.hotel_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(userRepository.existsByUsername(username)){
            AppUser user = userRepository.findByUsername(username);
            return new CustomUserDetails(user);
        }
        throw new UsernameNotFoundException("No user in DB with username: " +username);
    }
}
