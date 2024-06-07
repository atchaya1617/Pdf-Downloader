package com.example.Pdf.Dowloader.service;

import com.example.Pdf.Dowloader.entity.Employee;
import com.example.Pdf.Dowloader.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
