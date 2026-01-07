package com.wcl.gtc.userServices;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wcl.gtc.entities.User;
import com.wcl.gtc.enums.Role;
import com.wcl.gtc.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService  {

private final UserRepository userRepository;

    // Constructor Injection (Best Practice)
private final PasswordEncoder passwordEncoder; // ✅ ADD

    // Constructor Injection
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) { // ✅ ADD
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ Create User
    @Override
    public User createUser(User user) {

        // Email uniqueness check
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Default role safety (optional)
        if (user.getRole() == null) {
            user.setRole(Role.TRAINEE);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // ✅ Get All Users
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ✅ Update User
    @Override
    public User updateUser(String email, User user) {

        User existingUser = getUserByEmail(email);

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());
        // ✅ Encrypt password only if updated
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(existingUser);
    }

    // ✅ Delete User
    @Override
    public void deleteUser(String email) {

        User user = getUserByEmail(email);
        userRepository.delete(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
    }

}
