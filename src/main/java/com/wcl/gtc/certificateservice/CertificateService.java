package com.wcl.gtc.certificateservice;

import java.time.LocalDate;
import java.util.List;

import com.wcl.gtc.dto.CertificateResponse;


public interface CertificateService {

    CertificateResponse issueCertificate(String certificateId,String userEmail,String programName,LocalDate expiryDate);

    CertificateResponse getByCertificateId(String certificateId);

    List<CertificateResponse> getCertificatesByUserEmail(String email);

    List<CertificateResponse> getCertificatesByProgramName(String programName);

    List<CertificateResponse> getAllCertificates();
    // REVOKE CERTIFICATE
   // Certificate revokeCertificate(String certificateId);

    // MARK CERTIFICATE AS EXPIRED (manual or scheduler)
   // Certificate expireCertificate(String certificateId);
}

