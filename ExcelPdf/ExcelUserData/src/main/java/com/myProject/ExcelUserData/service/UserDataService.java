package com.myProject.ExcelUserData.service;

import com.myProject.ExcelUserData.model.UserData;
import com.myProject.ExcelUserData.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDataService {
    @Autowired
    private UserDataRepository userDataRepository;

    public List<UserData> getAllUserData() {
        return userDataRepository.findAll();
    }

    public void saveUserData(UserData userData) {
        userDataRepository.save(userData);
    }

    // Other methods for CRUD operations
}