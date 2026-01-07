package com.wcl.gtc.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wcl.gtc.entities.User;
@Repository

public interface UserRepository extends JpaRepository<User, Long> {

boolean existsByEmail(String email);
Optional<User> findByEmail(String email);
}
