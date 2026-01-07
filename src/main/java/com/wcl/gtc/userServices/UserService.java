package com.wcl.gtc.userServices;

import java.util.List;

import com.wcl.gtc.entities.User;

public interface UserService {
    User createUser(User user);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    User updateUser(String email, User user);
    void deleteUser(String email);
}
