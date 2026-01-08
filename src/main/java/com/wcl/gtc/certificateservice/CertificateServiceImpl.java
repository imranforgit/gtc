package com.wcl.gtc.certificateservice;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wcl.gtc.dto.CertificateResponse;
import com.wcl.gtc.entities.Certificate;
import com.wcl.gtc.entities.TrainingProgram;
import com.wcl.gtc.entities.User;
import com.wcl.gtc.enums.CertificateStatus;
import com.wcl.gtc.enums.Role;
import com.wcl.gtc.exception.ConflictException;
import com.wcl.gtc.repositories.CertificateRepository;
import com.wcl.gtc.repositories.TrainingProgramRepository;
import com.wcl.gtc.repositories.UserRepository;

import jakarta.transaction.Transactional;
//import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CertificateServiceImpl implements CertificateService {
private final CertificateRepository certificateRepository;
private final UserRepository userRepository;
private final TrainingProgramRepository trainingProgramRepository;

    public CertificateServiceImpl(CertificateRepository certificateRepository,UserRepository userRepository,TrainingProgramRepository trainingProgramRepository) {
        this.certificateRepository = certificateRepository;
        this.userRepository = userRepository;
        this.trainingProgramRepository = trainingProgramRepository;
    }

    private CertificateResponse mapToResponse(Certificate certificate) {
    CertificateResponse response = new CertificateResponse();
    response.setCertificateId(certificate.getCertificateId());
    response.setUserName(certificate.getUser().getName());
    response.setUserEmail(certificate.getUser().getEmail());
    response.setProgramName(certificate.getProgram().getName());
    response.setIssueDate(certificate.getIssueDate());
    response.setExpiryDate(certificate.getExpiryDate());
    response.setStatus(certificate.getStatus().name());
    return response;
    }

    @Override
    public CertificateResponse issueCertificate(
            String certificateId,
            String userEmail,
            String programName,
            LocalDate expiryDate) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.TRAINEE) {
            throw new RuntimeException("Certificate can be issued only to TRAINEE");
        }

        TrainingProgram program = trainingProgramRepository.findByName(programName)
                .orElseThrow(() -> new RuntimeException("Training program not found"));

        if (certificateRepository.existsByUserAndProgram(user, program)) {
            throw new RuntimeException("Certificate already issued for this program");
        }
        if (certificateRepository.existsById(certificateId)) {
            throw new ConflictException("Certificate already exists");
        }

        Certificate certificate = new Certificate();
        certificate.setCertificateId(certificateId);
        certificate.setUser(user);
        certificate.setProgram(program);
        certificate.setIssueDate(LocalDate.now());
        certificate.setExpiryDate(expiryDate);

        if (expiryDate != null && expiryDate.isBefore(LocalDate.now())) {
            certificate.setStatus(CertificateStatus.EXPIRED);
        } else {
            certificate.setStatus(CertificateStatus.VALID);
        }
        certificate=certificateRepository.save(certificate);
        return mapToResponse(certificate);
    }

    @Override
    public CertificateResponse getByCertificateId(String certificateId) {
        Certificate certificate=certificateRepository.findById(certificateId)
                .orElseThrow(() -> new RuntimeException("Certificate not found"));
        return mapToResponse(certificate);
    }

    @Override
    public List<CertificateResponse> getCertificatesByUserEmail(String email) {
        return certificateRepository.findByUser_Email(email).stream().map(this::mapToResponse).toList();
    } 

    @Override
    public List<CertificateResponse> getCertificatesByProgramName(String programName) {
        return certificateRepository.findByProgram_Name(programName).stream().map(this::mapToResponse).toList();
    }   

    @Override
    public List<CertificateResponse> getAllCertificates() {
        return certificateRepository.findAll().stream().map(this::mapToResponse).toList();
    }     
   
}
