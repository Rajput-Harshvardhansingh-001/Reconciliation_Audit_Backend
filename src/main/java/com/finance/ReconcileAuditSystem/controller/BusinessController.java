package com.finance.ReconcileAuditSystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.ReconcileAuditSystem.service.FileProcess;
import com.opencsv.CSVReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BusinessController {

@Autowired
    FileProcess fileProcess;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "mapping", required = false) String mappingJson
    ) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            Map<String, String> mapping = new HashMap<>();

            if (mappingJson != null && !mappingJson.isEmpty()) {
                mapping = mapper.readValue(mappingJson, Map.class);
            }

            System.out.println("File: " + file.getOriginalFilename());
            System.out.println("Mapping: " + mapping);

            fileProcess.processFile(file, mapping);

            return ResponseEntity.ok("Processed successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body("Processing failed: " + e.getMessage());
        }
    }

/*
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {

        try {
            String fileName = file.getOriginalFilename();

            if (fileName.endsWith(".csv")) {

                CSVReader reader = new CSVReader(
                        new InputStreamReader(file.getInputStream())
                );

                String[] row;
                while ((row = reader.readNext()) != null) {
                    System.out.println(Arrays.toString(row));
                }

                return ResponseEntity.ok("CSV processed");

            } else if (fileName.endsWith(".xlsx")) {

                Workbook workbook = new XSSFWorkbook(file.getInputStream());
                Sheet sheet = workbook.getSheetAt(0);

                for (Row row : sheet) {
                    for (Cell cell : row) {
                        System.out.print(cell + " | ");
                    }
                    System.out.println();
                }

                workbook.close();

                return ResponseEntity.ok("XLSX processed");

            } else {
                return ResponseEntity.badRequest().body("Only CSV/XLSX allowed");
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Processing failed");
        }
    }
*/




    //    @PostMapping("/upload")
//    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return ResponseEntity.badRequest().body("File is empty");
//        }
//
//        return ResponseEntity.ok("File received: " + file.getOriginalFilename());
//    }
}
