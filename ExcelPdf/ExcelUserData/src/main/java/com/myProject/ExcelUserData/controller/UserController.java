package com.myProject.ExcelUserData.controller;

import com.myProject.ExcelUserData.model.UserData;
import com.myProject.ExcelUserData.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserDataService userDataService;

    @PostMapping("/add-user")
    public ResponseEntity<String> addUser(@RequestBody UserData userData) {
        userDataService.saveUserData(userData);
        return ResponseEntity.ok("User added successfully");
    }

    @GetMapping("/get-users")
    public ResponseEntity<List<UserData>> getUsers() {
        List<UserData> users = userDataService.getAllUserData();
        return ResponseEntity.ok(users);
    }
}