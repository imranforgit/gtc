package com.wcl.gtc.security;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.wcl.gtc.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // Constructor injection
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Called automatically by Spring Security during authentication
     */
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        // Fetch user from database
        com.wcl.gtc.entities.User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with email: " + email));

        // Convert to Spring Security UserDetails
        return User
                .withUsername(user.getEmail())
                .password(user.getPassword())                  // encrypted password
                .authorities("ROLE_" + user.getRole().name())  // ROLE_ADMIN / ROLE_TRAINEE
                .build();
    }
}
