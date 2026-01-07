package com.wcl.gtc.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wcl.gtc.entities.Certificate;
import com.wcl.gtc.entities.TrainingProgram;
import com.wcl.gtc.entities.User;
import com.wcl.gtc.enums.CertificateStatus;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate,String> {

    Optional<Certificate> findByCertificateId(String certificateId);
    List<Certificate> findByUser_Email(String email); // Certificates by user email ,how many certicate a user have


    List<Certificate> findByProgram_Name(String name);// Certificates by program name, how many certificates a program have   

    boolean existsByUserAndProgram(User user, TrainingProgram program);
    List<Certificate> findByStatus(CertificateStatus status);
}
