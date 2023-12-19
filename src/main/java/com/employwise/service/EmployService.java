package com.employwise.service;

import com.employwise.model.Employ;
import com.employwise.payload.EmployRequest;
import com.employwise.payload.EmployResponse;
import com.employwise.payload.ManagerAndPathDTO;

import java.util.List;

public interface EmployService {
    EmployResponse createEmploy(EmployRequest employ, String managerPath, Employ manager);

    EmployResponse updateEmploy(EmployRequest request, Employ employ, String managerPath, Employ manager);

    void deleteEmploy(String id);

    EmployResponse getNthManager(String id, int n);

    ManagerAndPathDTO getManagerPath(String managerId);

    List<EmployResponse> getAllEmploys(int page, int size, String sortField, String sortOrder);

    Employ findById(String id);
}
