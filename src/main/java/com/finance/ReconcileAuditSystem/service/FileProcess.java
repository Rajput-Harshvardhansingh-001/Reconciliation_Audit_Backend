package com.finance.ReconcileAuditSystem.service;

import com.finance.ReconcileAuditSystem.model.Entities.FileMappingEntity;
import com.finance.ReconcileAuditSystem.repository.FileMapRepo;
import com.opencsv.CSVReader;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class FileProcess {

    @Autowired
    FileMapRepo mapRepo;

    public void processFile(MultipartFile file, Map<String, String> mapping) throws Exception {

        String fileName = file.getOriginalFilename();

        // ✅ fallback mapping (IMPORTANT)
        if (mapping == null || mapping.isEmpty()) {
            mapping = getDefaultMapping();
            System.out.println("⚠️ Using default mapping: " + mapping);
        }

        if (fileName.endsWith(".csv")) {
            processCSV(file, mapping);
        } else if (fileName.endsWith(".xlsx")) {
            processXLSX(file, mapping);
        } else {
            throw new RuntimeException("Unsupported file type");
        }
    }

    private String normalize(String str) {
        return str.trim().toLowerCase().replace("_", "").replace(" ", "");
    }

    // ================= CSV =================
    private void processCSV(MultipartFile file, Map<String, String> mapping) throws Exception {

        CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()));

        String[] header = reader.readNext();

        Map<String, Integer> headerIndex = new HashMap<>();
        for (int i = 0; i < header.length; i++) {
            String normalizedHeader = normalize(header[i]);
            headerIndex.put(normalizedHeader, i);
            //headerIndex.put(header[i].trim().toLowerCase(), i);
        }

        String[] row;

        while ((row = reader.readNext()) != null) {

            FileMappingEntity entity = new FileMappingEntity();


            for (String fileColumn : mapping.keySet()) {

                String normalizedColumn = normalize(fileColumn);

                Integer index = headerIndex.get(normalizedColumn);
                //Integer index = headerIndex.get(fileColumn.trim().toLowerCase());
                //if (index == null) continue; // safe
                if (index == null) {
                    System.out.println("❌ Column not found: " + fileColumn);
                    continue;
                }
                //String value = row[index];
                String value = (row[index] == null) ? "" : row[index];

                mapField(entity, mapping.get(fileColumn), value);
            }

            mapRepo.save(entity);
        }

    }


    private String getCellValue(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();

            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return new SimpleDateFormat("dd-MMM-yyyy").format(cell.getDateCellValue());
                }
                return String.valueOf(cell.getNumericCellValue());

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            default:
                return "";
        }
    }

    // ================= XLSX =================
    private void processXLSX(MultipartFile file, Map<String, String> mapping) throws Exception {

        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        Row headerRow = sheet.getRow(0);

        Map<String, Integer> headerIndex = new HashMap<>();
        for (Cell cell : headerRow) {
            String normalizedHeader = normalize(cell.getStringCellValue());
            //headerIndex.put(cell.getStringCellValue().trim().toLowerCase(), cell.getColumnIndex());
            headerIndex.put(normalizedHeader, cell.getColumnIndex());
        }

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            Row row = sheet.getRow(i);
            if (row == null) continue;

            FileMappingEntity entity = new FileMappingEntity();

            for (String fileColumn : mapping.keySet()) {

                //Integer index = headerIndex.get(fileColumn.trim().toLowerCase());
                //if (index == null) continue;
                String normalizedColumn = normalize(fileColumn);

                Integer index = headerIndex.get(normalizedColumn);

                if (index == null) {
                    System.out.println("❌ Column not found: " + fileColumn);
                    continue;
                }


                Cell cell = row.getCell(index);
                //String value = (cell == null) ? "" : cell.toString();
                //String value = getCellValue(cell);
                String value = (cell == null) ? "" : cell.toString();

                mapField(entity, mapping.get(fileColumn), value);
            }

            mapRepo.save(entity);
        }

        workbook.close();
    }

    // ================= FIELD MAPPING =================
    private void mapField(FileMappingEntity entity, String field, String value) {

        if (value == null) value = "";
        value = value.trim();

        try {
            switch (field) {

                case "account_holder_name":
                    entity.setAccountHolderName(value);
                    break;

                case "account_id":
                    entity.setAccountId(value);
                    break;

                case "description":
                    entity.setDescription(value);
                    break;

                case "deposits":
                    entity.setDeposits(value.isEmpty() ? 0 : Double.parseDouble(value));
                    break;

                case "withdrawal":
                    entity.setWithdrawal(value == null || value.isEmpty() ? 0 : Double.parseDouble(value));
                    break;

                case "balance":
                    entity.setBalance(value.isEmpty() ? 0 : Double.parseDouble(value));
                    break;

                case "date":
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
                        entity.setDate(LocalDate.parse(value, formatter));
                    } catch (Exception e) {
                        System.out.println("⚠️ Date parse failed: " + value);
                    }
                    break;

                default:
                    System.out.println("Unknown field: " + field);

            }
            System.out.println("Column: " + entity + " -> Field: " + field + " -> Value: " + value);

        } catch (Exception e) {
            System.out.println("⚠️ Error parsing field: " + field + " value: " + value);
        }
    }

    // ================= DEFAULT MAPPING =================
    private Map<String, String> getDefaultMapping() {
        Map<String, String> map = new HashMap<>();

        map.put("account_holder_name", "account_holder_name");//account_holder_name
        map.put("account_id", "account_id");//account_id
        map.put("description", "description");
        map.put("deposits", "deposits");
        map.put("withdrawal", "withdrawal");
        map.put("balance", "balance");
        map.put("date", "date");

        return map;
    }

}
