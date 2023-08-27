package com.myProject.ExcelUserData.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import com.myProject.ExcelUserData.model.UserData;
import com.myProject.ExcelUserData.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
public class PDFController {

    @Autowired
    private UserDataService userDataService;

    @GetMapping("/generate-pdf")
    public ResponseEntity<InputStreamResource> generatePDF() throws DocumentException {
        // Retrieve user data from UserDataService
        // For demonstration purposes, let's assume you have a method userDataService.getAllUserData()

        // Create a ByteArrayOutputStream to hold the PDF content
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Create a new PDF document using PdfDocument and PdfWriter
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(byteArrayOutputStream));

        // Create a Document instance
        Document document = new Document(pdfDocument.getPageSize());

        // Retrieve user data from the service
        List<UserData> userDataList = userDataService.getAllUserData();

        // Add user data to the PDF
        for (UserData userData : userDataList) {
            document.add(new Paragraph("User ID: " + userData.getId()));
            document.add(new Paragraph("Name: " + userData.getName()));
            document.add(new Paragraph("Email: " + userData.getEmail()));
            document.add(new Paragraph("------------------------------------------"));
        }

        // Close the document
        document.close();

        // Create an InputStreamResource from the ByteArrayOutputStream
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        // Prepare HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=userdata.pdf");

        // Set the content type for the response
        MediaType mediaType = MediaType.APPLICATION_PDF;
        headers.setContentType(mediaType);

        // Create and return the ResponseEntity
        return new ResponseEntity<>(new InputStreamResource(inputStream), headers, HttpStatus.OK);
    }
}
