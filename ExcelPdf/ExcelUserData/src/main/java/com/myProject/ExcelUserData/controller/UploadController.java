package com.myProject.ExcelUserData.controller;

import com.myProject.ExcelUserData.model.UserData;
import com.myProject.ExcelUserData.service.UserDataService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Iterator;

@RestController
public class UploadController {
    @Autowired
    private UserDataService userDataService;

    @PostMapping("/upload")
    public String uploadExcel(@RequestParam("file") MultipartFile file) {
        try {
            // Get the input stream from the uploaded file
            Workbook workbook = WorkbookFactory.create(file.getInputStream());

            // Assuming the first sheet is the one containing data
            Sheet sheet = workbook.getSheetAt(0);

            // Iterate through rows and extract data
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                // Assuming the first column contains name and the second column contains email
                Cell nameCell = row.getCell(0);
                Cell emailCell = row.getCell(1);

                String name = nameCell.getStringCellValue();
                String email = emailCell.getStringCellValue();

                // Create a UserData object and save it using UserDataService
                UserData userData = new UserData();
                userData.setName(name);
                userData.setEmail(email);
                userDataService.saveUserData(userData);
            }

            // Close the workbook
            workbook.close();

        } catch (IOException e) {
            // Handle the exception appropriately
            e.printStackTrace();
        }

        // Redirect back to a page after uploading
        return "redirect:/";
    }
}
