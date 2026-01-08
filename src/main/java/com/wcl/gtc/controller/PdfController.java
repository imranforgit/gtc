package com.wcl.gtc.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wcl.gtc.pdfservices.PdfService;

@RestController
@RequestMapping("/api/certificate")
public class PdfController {
    @Autowired
    private PdfService pdfService;
    @GetMapping("/generate/{certificateNo}")
    public ResponseEntity<byte[]> generateCertificate(@PathVariable String certificateNo) {
        byte[] pdfBytes = pdfService.generateGasTestingCertificate(certificateNo);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=GasTestingCertificate.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}