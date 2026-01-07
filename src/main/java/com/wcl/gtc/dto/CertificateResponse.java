package com.wcl.gtc.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificateResponse {

    private String certificateId;
    private String userName;
    private String userEmail;
    private String programName;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String status;
}
