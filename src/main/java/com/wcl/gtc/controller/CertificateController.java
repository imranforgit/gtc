package com.wcl.gtc.controller;


import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.wcl.gtc.certificateservice.CertificateService;
import com.wcl.gtc.dto.CertificateRequest;
import com.wcl.gtc.dto.CertificateResponse;


import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/certificates")
public class CertificateController {

    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/issue")
    public CertificateResponse issueCertificate(@Valid @RequestBody CertificateRequest request) {

        return certificateService.issueCertificate(
                request.getCertificateId(), request.getUserEmail(), request.getProgramName(), request.getExpiryDate());
    }  

    @GetMapping("/{certificateId}")
    public CertificateResponse getById(@PathVariable String certificateId) {
        return certificateService.getByCertificateId(certificateId);
    }

    @GetMapping("/user/{email}")
    public List<CertificateResponse> getByUser(@PathVariable String email) {
        return certificateService.getCertificatesByUserEmail(email);
    }

    @GetMapping("/program/{name}")
    public List<CertificateResponse> getByProgram(@PathVariable String name) {
        return certificateService.getCertificatesByProgramName(name);
    }

    @GetMapping
    public List<CertificateResponse > getAll() {
        return certificateService.getAllCertificates();
    }
}
