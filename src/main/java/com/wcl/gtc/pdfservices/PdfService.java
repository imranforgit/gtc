package com.wcl.gtc.pdfservices;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.wcl.gtc.certificateservice.CertificateService;
import com.wcl.gtc.dto.CertificateResponse;
import com.wcl.gtc.qrutil.QrUtil;

import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Service
public class PdfService {
        private CertificateService certificateService;
        private QrUtil qrUtil;
        public PdfService(CertificateService certificateService, QrUtil qrUtil) {
            this.certificateService = certificateService;
            this.qrUtil = qrUtil;       
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
            document.add(new Paragraph("\n has successfully undergone and completed the")
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
        //     document.add(new Paragraph("Certificate No.: " + certificateNo)
        //             .setFont(regularFont)
        //             .setFontSize(12)
        //             .setTextAlignment(TextAlignment.LEFT));
        //     document.add(new Paragraph("Date of Issue: " + fromDate)
        //             .setFont(regularFont)
        //             .setFontSize(12)
        //             .setTextAlignment(TextAlignment.LEFT));
         String verifyUrl = "https://localhost:9786/verify/" + certificateNo;

        // // Generate QR Image
        // BufferedImage qrImage = qrUtil.generateQRCode(verifyUrl, 200, 200);             

        // // Convert to ImageData
        // ImageData qrImageData = ImageDataFactory.create(qrUtil.bufferedImageToBytes(qrImage));

        // // Add QR image to PDF
        // Image qrPdfImage = new Image(qrImageData);
        // qrPdfImage.setWidth(100);
        // qrPdfImage.setHorizontalAlignment(HorizontalAlignment.RIGHT);  
        // document.add(qrPdfImage);
       // Create container Div
        // Container Div
       // Table with 2 columns: left 70%, right 30%
// 2-column table: left 70%, right 30%
// ====== CERTIFICATE NO + DATE + QR SAME LINE ======

// Create Table with 2 columns: left(70%) and right(30%)
        // float[] widths = {95,5};
        // Table infoTable = new Table(widths);
        // infoTable.setWidth(UnitValue.createPercentValue(100));
        // infoTable.setBorder(Border.NO_BORDER);

        // // LEFT SIDE TEXT BLOCK
        // Div leftDiv = new Div()
        //         .add(new Paragraph("Certificate No.: " + certificateNo)
        //                 .setFontSize(12))
        //         .add(new Paragraph("Date of Issue: " + fromDate)
        //                 .setFontSize(12));

        // Cell leftCell = new Cell()
        //         .add(leftDiv)
        //         .setBorder(Border.NO_BORDER)
        //         .setVerticalAlignment(VerticalAlignment.TOP);
            

        // infoTable.addCell(leftCell);

        // // RIGHT SIDE QR BLOCK
        // BufferedImage qrImage = qrUtil.generateQRCode(verifyUrl, 120, 120);
        // ImageData qrImageData = ImageDataFactory.create(qrUtil.bufferedImageToBytes(qrImage));
        // Image qrImg = new Image(qrImageData)
        //         .setWidth(80)
        //         .setHorizontalAlignment(HorizontalAlignment.RIGHT);

        // Cell rightCell = new Cell()
        //         .add(qrImg)
        //         .setBorder(Border.NO_BORDER)
        //         .setVerticalAlignment(VerticalAlignment.TOP)
        //         .setTextAlignment(TextAlignment.RIGHT);

        // infoTable.addCell(rightCell);

        // // Add table to document
        // document.add(infoTable);
        Table table = new Table(new float[]{350f, 120f}).setWidth(UnitValue.createPointValue(470));

        table.addCell(
        new Cell().add(new Paragraph(
                "Certificate No.: " + certificateNo + "\nDate of Issue: " + fromDate
                ).setFontSize(12))
                .setBorder(Border.NO_BORDER)
        );

        BufferedImage qr = qrUtil.generateQRCode(verifyUrl, 120, 120);
        Image qrImg = new Image(ImageDataFactory.create(qrUtil.bufferedImageToBytes(qr)))
                .setAutoScale(false);

        table.addCell(
        new Cell().add(qrImg)
                .setBorder(Border.NO_BORDER)
                .setVerticalAlignment(VerticalAlignment.TOP)
                .setTextAlignment(TextAlignment.RIGHT)
        );

        document.add(table);









            // Signatures
            float[] columnWidths = {280, 280};
        table = new Table(columnWidths);
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


