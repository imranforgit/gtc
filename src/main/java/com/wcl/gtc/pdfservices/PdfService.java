package com.wcl.gtc.pdfservices;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.wcl.gtc.certificateservice.CertificateService;
import com.wcl.gtc.dto.CertificateResponse;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {
        private CertificateService certificateService;
        public PdfService(CertificateService certificateService) {
            this.certificateService = certificateService;
        }       

public byte[] generateGasTestingCertificate(String certificateNo) {
        String name=" ";String empNo=" ";String fromDate="";String toDate="";String certificateId="";String certificateStatus="";String programName="";    
        try{
        CertificateResponse certificateResponse=certificateService.getByCertificateId(certificateNo);
 
        name=certificateResponse.getUserName();
        empNo=certificateResponse.getUserEmail();  
        fromDate=certificateResponse.getIssueDate().toString();
        toDate=certificateResponse.getExpiryDate().toString();
        certificateId=certificateResponse.getCertificateId();    
        certificateStatus=certificateResponse.getStatus();
        programName=certificateResponse.getProgramName();     
        }       
        catch(Exception e){
            System.out.println("Error in fetching certificate details.");
        }    
          
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            com.itextpdf.kernel.pdf.PdfWriter writer = new com.itextpdf.kernel.pdf.PdfWriter(baos);
            com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(writer);
            Document document = new Document(pdfDoc, PageSize.A4);
            document.setMargins(50, 50, 50, 50);

            // Draw border
            document.setBorder(new SolidBorder(ColorConstants.BLACK, 2));

            // Add full-page subtle watermark logo
            try {
                Image watermark = new Image(ImageDataFactory.create("src/main/resources/static/watermark.png"));

                // Get page size
                float pageWidth = pdfDoc.getDefaultPageSize().getWidth();
                float pageHeight = pdfDoc.getDefaultPageSize().getHeight();

                // Scale watermark to cover most of the page
                watermark.scaleToFit(pageWidth * 0.8f, pageHeight * 0.8f);

                // Center watermark
                watermark.setFixedPosition(
                        (pageWidth - watermark.getImageScaledWidth()) / 2,
                        (pageHeight - watermark.getImageScaledHeight()) / 2
                );

                // Rotate diagonally
               // watermark.setRotationAngle(Math.toRadians(45));

                // Subtle opacity
                watermark.setOpacity(0.08f);

                document.add(watermark);
            } catch (Exception e) {
                System.out.println("Watermark logo not found.");
            }

            // WCL Logo at top
            try {
                Image logo = new Image(ImageDataFactory.create("src/main/resources/static/wcl_logo.png"));
                logo.setWidth(100);
                logo.setHorizontalAlignment(HorizontalAlignment.CENTER);
                document.add(logo);
            } catch (Exception e) {
                System.out.println("Top logo not found.");
            }

            // Fonts
            com.itextpdf.kernel.font.PdfFont boldFont = com.itextpdf.kernel.font.PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA_BOLD);
            com.itextpdf.kernel.font.PdfFont regularFont = com.itextpdf.kernel.font.PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA);

            // Title
            document.add(new Paragraph("\nWESTERN COALFIELDS LIMITED")
                    .setFont(boldFont)
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("A Miniratna Company (A Subsidiary of Coal India Limited)")
                    .setFont(regularFont)
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("\nGAS TESTING CERTIFICATE\n")
                    .setFont(boldFont)
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER));

            // Employee Details
            document.add(new Paragraph("This is to certify that")
                    .setFont(regularFont)
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph(name)
                    .setFont(boldFont)
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Employee No.: " + empNo)
                    .setFont(regularFont)
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("\nhas successfully undergone and completed the")
                    .setFont(regularFont)
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("GAS TESTING TRAINING PROGRAMME")
                    .setFont(boldFont)
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("conducted by Western Coalfields Limited during the period from " + fromDate + " to " + toDate + ".\n")
                    .setFont(regularFont)
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("During the training period, he was imparted theoretical and practical knowledge related to gas detection techniques, safety procedures, and statutory provisions applicable to mine safety.")
                    .setFont(regularFont)
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.JUSTIFIED));
            document.add(new Paragraph("His conduct and performance during the training were found to be satisfactory.\n")
                    .setFont(regularFont)
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.JUSTIFIED));

            // Certificate Number and Date
            document.add(new Paragraph("Certificate No.: " + certificateNo)
                    .setFont(regularFont)
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.LEFT));
            document.add(new Paragraph("Date of Issue: " + "")
                    .setFont(regularFont)
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.LEFT));

            // Signatures
            float[] columnWidths = {280, 280};
            Table table = new Table(columnWidths);
            table.setMarginTop(50);
            table.addCell(new Paragraph("____________________________\nTraining Manager\nWestern Coalfields Limited")
                    .setFont(regularFont)
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(null));
            table.addCell(new Paragraph("____________________________\nGeneral Manager (Safety)\nWestern Coalfields Limited")
                    .setFont(regularFont)
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBorder(null));

            document.add(table);
            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
       
            
    }
}


