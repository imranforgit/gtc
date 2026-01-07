package com.wcl.gtc.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificateRequest {

    @NotBlank(message = "Certificate id  is required")
    private String certificateId;
    @Email(message = "Invalid email format")
    @NotBlank(message = " user email is required")
    private String userEmail;
    @NotBlank(message = "Program name is required")
    private String programName;
    private LocalDate expiryDate; // optional  
}

