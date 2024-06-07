package com.example.Pdf.Dowloader.controller;

import com.example.Pdf.Dowloader.entity.Employee;
import com.example.Pdf.Dowloader.service.EmployeeService;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lowagie.text.Document;


import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/download-pdf")
    public ResponseEntity<byte[]> downloadPdf() {
        List<Employee> employees = employeeService.getAllEmployees();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            for (Employee employee : employees) {
                document.add(new Paragraph("ID: " + employee.getId()));
                document.add(new Paragraph("Name: " + employee.getName()));
                document.add(new Paragraph("Position: " + employee.getPosition()));
                document.add(new Paragraph("Salary: " + employee.getSalary()));
                document.add(new Paragraph(" "));
            }

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=employees.pdf");

        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);
    }
}

