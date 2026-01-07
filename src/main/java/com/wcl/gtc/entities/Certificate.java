package com.wcl.gtc.entities;

import java.time.LocalDate;

import com.wcl.gtc.enums.CertificateStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "certificates")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Certificate {

    @Id
    @Column(length = 30)
    private String certificateId; // GT-2025-00001

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // must be TRAINEE

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private TrainingProgram program;

    private LocalDate issueDate;
    private LocalDate expiryDate;

    // private int trainingHours;
    // private String trainingMode;
    // private String issuedBy;
    // private String authorizedSignatory;  for making certificate more official

    @Enumerated(EnumType.STRING)
    private CertificateStatus status; // VALID, EXPIRED, REVOKED
}
