package com.comunus.ams.controller;

import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.comunus.ams.model.FileModel;
import com.comunus.ams.service.FileService;

import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/v1")
public class WorkingHoursController  {
	
	
	@Autowired
	private FileService fileService;
	
	@GetMapping("/")
	public String goToHome() {
        log.info("Inside the WorkingHoursController");
		return "index";
	}
	@PostMapping("/upload")
	public ResponseEntity<List<Map<String, String>>> uploadfile(@RequestParam("files") MultipartFile[] files) throws IOException {
	    List<Map<String, Object>> responses = new ArrayList<>();
	    Map<String, Map<String, String>> employeeDataMap = new HashMap<>(); 
	    Map<String, List<String>> file1Data = new HashMap<>();  // For In Time
	    Map<String, List<String>> file2Data = new HashMap<>();  // For Out Time

        log.info("Starting file upload process...");

	    // Loop through uploaded files
	    for (MultipartFile file : files) {
	        String originalFilename = file.getOriginalFilename();
            log.info("Processing file: {}", originalFilename);

	        if (originalFilename == null || 
	            (!originalFilename.endsWith(".xls") && !originalFilename.endsWith(".xlsx"))) {
                log.warn("Invalid file type: {}", originalFilename);

	            Map<String, Object> errorResponse = new HashMap<>();
	            errorResponse.put("fileName", originalFilename);
	            errorResponse.put("message", "Invalid file type. Please upload an Excel file (.xls or .xlsx).");
	            responses.add(errorResponse);
	            continue; // Skip to the next file
	        }

	        try {
	            byte[] fileData = file.getBytes();
	            fileService.saveFile(originalFilename, fileData);

	            // Read the file and extract data
	            if (originalFilename.contains("file1")) {
	            	 log.info("Reading In Time data from file: {}", originalFilename);
	                readExcelFile(fileData, file1Data, originalFilename);  // Read In Time data from file1
	            } else if (originalFilename.contains("file2")) {
	            	log.info("Reading Out Time data from file: {}", originalFilename);
	                readExcelFile(fileData, file2Data, originalFilename);  // Read Out Time data from file2
	            }
	            
                log.info("Reading employee data from file: {}", originalFilename);
	            readExcelFile1(fileData, employeeDataMap, originalFilename);


	            Map<String, Object> response = new HashMap<>();
	            response.put("fileName", originalFilename);
	            response.put("contentType", file.getContentType());
	            response.put("size", file.getSize());
	            response.put("message", "File uploaded and data extracted successfully.");
	            responses.add(response);

	        } catch (IOException e) {
                log.error("Error processing file: {}", originalFilename, e);
	            Map<String, Object> errorResponse = new HashMap<>();
	            errorResponse.put("fileName", originalFilename);
	            errorResponse.put("message", "Error processing file: " + e.getMessage());
	            responses.add(errorResponse);
	        }
	    }

        log.info("Extracting non-working hours...");
	 // After both files are read, calculate non-working hours
	    List<Map<String, String>> nonWorkingData = extractNonWorkingHours(file1Data, file2Data);

	    // Convert non-working data into a map where the key is "Emp Id-Date" and value is "Non Working Hours"
	    Map<String, String> nonWorkingHoursMap = new HashMap<>();
	    for (Map<String, String> entry : nonWorkingData) {
	        String empIdDate = entry.get("Emp Id") + "-" + entry.get("Date");
	        nonWorkingHoursMap.put(empIdDate, entry.get("Non Working Hours"));
	    }
	    
	    // Create and save the Excel file
	    List<Map<String, String>> finalData = new ArrayList<>();
	    for (Map<String, String> entry : employeeDataMap.values()) {
	        finalData.add(entry);
	    }

        log.info("Creating Excel file for employee data...");
	    byte[] excelFileData = createExcelFile(finalData,nonWorkingHoursMap);
	    
	    byte[] nonWorkingExcelFileData = createNonWorkingExcelFile(nonWorkingData);

//	    List<Map<String, String>> nonWorkingData = extractNonWorkingHours(file1Data, file2Data);
//	    byte[] nonWorkingExcelFileData = createNonWorkingExcelFile(nonWorkingData);

	    // Save the file to disk
	    Path attendanceFolderPath = Paths.get("C:/Attendance");
	    if (!Files.exists(attendanceFolderPath)) {
	        try {
	            Files.createDirectories(attendanceFolderPath); // Create Attendance folder if it doesn't exist
                log.info("Created attendance directory at C:/Attendance");
	        } catch (IOException e) {
	        	log.error("Error creating attendance folder", e);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(null); // Handle folder creation failure
	        }
	    }

	    Path filePath = attendanceFolderPath.resolve("EmployeeData.xlsx");
	    
	    try {
	        Files.write(filePath, excelFileData);
            log.info("Employee data saved to file: {}", filePath);
	    } catch (IOException e) {
            log.error("Error saving employee data file", e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(null); // Handle save failure
	    }
	    
	    // Save NonWorkingHours file
//	    Path nonWorkingFilePath = attendanceFolderPath.resolve("NonWorkingHours.xlsx");
//	    try {
//	        Files.write(nonWorkingFilePath, nonWorkingExcelFileData);
//	    } catch (IOException e) {
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//	                .body(null); // Handle save failure
//	    }
	    
	    boolean fileSaved = fileService.saveFile("EmployeeData.xlsx", excelFileData); // Saving the new file

	    if (!fileSaved) {
            log.error("Error saving file through file service.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Handle save failure
	    }
	    
	    // Return the download link along with other response data
	    String downloadLink = "/ams/v1/download/EmployeeData.xlsx"; // This is the URL to download the file
	    Map<String, String> response = new HashMap<>();
	    response.put("message", "File uploaded and saved successfully");
	    response.put("downloadLink", downloadLink);

        log.info("File upload and processing completed successfully.");
	    return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonList(response));

	    //return ResponseEntity.status(HttpStatus.OK).body(finalData);
	}
	
	private void readExcelFile1(byte[] fileData, Map<String, Map<String, String>> employeeDataMap, String filename) throws IOException {
        log.debug("Reading file: {}", filename);
		try (ByteArrayInputStream inputStream = new ByteArrayInputStream(fileData);
	         Workbook workbook = fileData.length > 0 && fileData[0] == (byte) 0xD0 ? new HSSFWorkbook(inputStream) : new XSSFWorkbook(inputStream)) {

	        Sheet firstSheet = workbook.getSheetAt(0);
	        for (Row row : firstSheet) {
	            Cell cell0 = row.getCell(1); // Emp Id
	            Cell cell1 = row.getCell(2); // Date
	            Cell cell2 = row.getCell(3); // Time

	            if (cell0 != null && cell1 != null && cell2 != null) {
	                String empId = cell0.toString();
	                String date = cell1.toString();
	                String timeValue = "";

	                if (cell2.getCellType() == CellType.NUMERIC) {
	                    double numericValue = cell2.getNumericCellValue();
	                    timeValue = LocalTime.ofSecondOfDay((long) (numericValue * 86400)).toString();
	                }

	                String key = empId + "-" + date; // Unique key for employee ID and date
	                if (filename.contains("file1")) { // In Time
	                    if (!employeeDataMap.containsKey(key) || employeeDataMap.get(key).get("In Time") == null) {
	                        Map<String, String> rowData = new LinkedHashMap<>();
	                        rowData.put("Emp Id", empId);
	                        rowData.put("Date", date);
	                        rowData.put("In Time", timeValue);
	                        employeeDataMap.put(key, rowData);
	                    }
	                } else if (filename.contains("file2")) { // Out Time
	                    if (employeeDataMap.containsKey(key)) {
	                        employeeDataMap.get(key).put("Out Time", timeValue);
	                        // Calculate Total Working Hours
	                        String inTimeStr = employeeDataMap.get(key).get("In Time");
	                        if (inTimeStr != null) {
	                            LocalTime inTime = LocalTime.parse(inTimeStr);
	                            LocalTime outTime = LocalTime.parse(timeValue);
	                            Duration duration = Duration.between(inTime, outTime);
	                            long hours = duration.toHours();
	                            long minutes = duration.toMinutes() % 60;
	                            employeeDataMap.get(key).put("Total Working Hours", String.format("%02d:%02d", hours, minutes));
	                        }
	                    }
	                }
	            }
	        }
	    } catch (Exception e) {
            log.error("Error reading Excel file: {}", filename, e);
	        e.printStackTrace();
	        throw new IOException("Error reading Excel file: " + e.getMessage());
	    }
	}
	
	private void readExcelFile(byte[] fileData, Map<String, List<String>> dataMap, String filename) throws IOException {
	    try (ByteArrayInputStream inputStream = new ByteArrayInputStream(fileData);
	         Workbook workbook = fileData.length > 0 && fileData[0] == (byte) 0xD0 ? new HSSFWorkbook(inputStream) : new XSSFWorkbook(inputStream)) {

	        Sheet firstSheet = workbook.getSheetAt(0);
	        for (Row row : firstSheet) {
	            Cell cell0 = row.getCell(1); // Emp Id
	            Cell cell1 = row.getCell(2); // Date
	            Cell cell2 = row.getCell(3); // Time

	            if (cell0 != null && cell1 != null && cell2 != null) {
	                String empId = cell0.toString();
	                String date = cell1.toString();
	                String timeValue = "";

	                if (cell2.getCellType() == CellType.NUMERIC) {
	                    double numericValue = cell2.getNumericCellValue();
	                    timeValue = LocalTime.ofSecondOfDay((long) (numericValue * 86400)).toString();
	                }

	                String key = empId + "_" + date; // Combine empId and date to handle multiple records for the same employee and date
	                dataMap.computeIfAbsent(key, k -> new ArrayList<>()).add(timeValue);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new IOException("Error reading Excel file: " + e.getMessage());
	    }
	}

//	private void calculateNonWorkingHours(Map<String, List<String>> file1Data, Map<String, List<String>> file2Data) {
//	    // Iterate over each date in file1 and file2
//	    for (String date : file1Data.keySet()) {
//	        if (file2Data.containsKey(date)) {
//	            List<String> file1Times = file1Data.get(date);
//	            List<String> file2Times = file2Data.get(date);
//
//	            Duration totalNonWorkingDuration = Duration.ZERO;
//
//	            // We need to make pairs of file1 and file2 for the same date
//	            int minSize = Math.min(file1Times.size(), file2Times.size());
//	            
//	            // We loop through the entries, calculating the difference in times
//	            for (int i = 1; i < minSize; i++) { // Start from second element in file1
//	                LocalTime file1InTime = LocalTime.parse(file1Times.get(i));   // file1[i]
//	                LocalTime file2InTime = LocalTime.parse(file2Times.get(i - 1)); // file2[i-1]
//
//	                // Calculate difference between file1[i] and file2[i-1]
//	                Duration nonWorkingDuration = Duration.between(file2InTime, file1InTime);
//	                totalNonWorkingDuration = totalNonWorkingDuration.plus(nonWorkingDuration);
//	            }
//
//	            // Format the total non-working hours as hours, minutes, and seconds
//	            long nonWorkingHours = totalNonWorkingDuration.toHours();
//	            long nonWorkingMinutes = totalNonWorkingDuration.toMinutes() % 60;
//	            long nonWorkingSeconds = totalNonWorkingDuration.getSeconds() % 60;
//
//	            String nonWorkingFormatted = String.format("%02d:%02d:%02d", nonWorkingHours, nonWorkingMinutes, nonWorkingSeconds);
//	            System.out.println(":"+nonWorkingFormatted);
//
//	            System.out.printf("%s - non-working hours = %02d:%02d:%02d%n", date, nonWorkingHours, nonWorkingMinutes, nonWorkingSeconds);
//	        }
//	    }
//	}
	
	private List<Map<String, String>> extractNonWorkingHours(Map<String, List<String>> file1Data, Map<String, List<String>> file2Data) {
	    List<Map<String, String>> nonWorkingData = new ArrayList<>();

	    for (String date : file1Data.keySet()) {
	        if (file2Data.containsKey(date)) {
	            List<String> file1Times = file1Data.get(date);
	            List<String> file2Times = file2Data.get(date);

	            Duration totalNonWorkingDuration = Duration.ZERO;

	            // We need to make pairs of file1 and file2 for the same date
	            int minSize = Math.min(file1Times.size(), file2Times.size());

	            // We loop through the entries, calculating the difference in times
	            for (int i = 1; i < minSize; i++) { // Start from second element in file1
	                LocalTime file1InTime = LocalTime.parse(file1Times.get(i));   // file1[i]
	                LocalTime file2InTime = LocalTime.parse(file2Times.get(i - 1)); // file2[i-1]

	                // Calculate difference between file1[i] and file2[i-1]
	                Duration nonWorkingDuration = Duration.between(file2InTime, file1InTime);
	                totalNonWorkingDuration = totalNonWorkingDuration.plus(nonWorkingDuration);
	            }

	            // Format the total non-working hours as hours, minutes, and seconds
	            long nonWorkingHours = totalNonWorkingDuration.toHours();
	            long nonWorkingMinutes = totalNonWorkingDuration.toMinutes() % 60;
	            long nonWorkingSeconds = totalNonWorkingDuration.getSeconds() % 60;

	            String nonWorkingFormatted = String.format("%02d:%02d:%02d", nonWorkingHours, nonWorkingMinutes, nonWorkingSeconds);

	            // Add the data to the list
	            Map<String, String> nonWorkingEntry = new HashMap<>();
	            nonWorkingEntry.put("Emp Id", date.split("_")[0]);  // Extract Emp Id from date
	            nonWorkingEntry.put("Date", date.split("_")[1]);    // Extract Date from date
	            nonWorkingEntry.put("Non Working Hours", nonWorkingFormatted);

	            nonWorkingData.add(nonWorkingEntry);
	        }
	    }

	    return nonWorkingData;
	}

	
	private byte[] createNonWorkingExcelFile(List<Map<String, String>> nonWorkingData) throws IOException {
	   
	    Collections.sort(nonWorkingData, Comparator.comparingDouble(entry -> Double.parseDouble(entry.get("Emp Id"))));
		
		try (Workbook workbook = new XSSFWorkbook();
	         ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

	        Sheet sheet = workbook.createSheet("Non Working Hours");
	        Row headerRow = sheet.createRow(0);

	        // Create header row
	        String[] headers = {"Emp Id", "Date", "Non Working Hours"};
	        for (int i = 0; i < headers.length; i++) {
	            headerRow.createCell(i).setCellValue(headers[i]);
	        }

	        // Fill data rows
	        int rowNum = 1;
	        for (Map<String, String> entry : nonWorkingData) {
	            Row row = sheet.createRow(rowNum++);
	            row.createCell(0).setCellValue(entry.getOrDefault("Emp Id", ""));
	            row.createCell(1).setCellValue(entry.getOrDefault("Date", ""));
	            row.createCell(2).setCellValue(entry.getOrDefault("Non Working Hours", "00:00:00"));
	        }

	        workbook.write(outputStream);
	        return outputStream.toByteArray();
	    }
	}


	private byte[] createExcelFile(List<Map<String, String>> finalData,Map<String, String> nonWorkingHoursMap) throws IOException {
	    // Sort data by "Emp Id"
	    Collections.sort(finalData, Comparator.comparingDouble(entry -> Double.parseDouble(entry.get("Emp Id"))));

	    try (Workbook workbook = new XSSFWorkbook();
	         ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

	        Sheet sheet = workbook.createSheet("Employee Data");
	        Row headerRow = sheet.createRow(0);

	        // Create header row
	        String[] headers = {"Emp Id", "Date", "In Time", "Out Time", "Total Working Hours","Non Working Hours"};
	        for (int i = 0; i < headers.length; i++) {
	            headerRow.createCell(i).setCellValue(headers[i]);
	        }

	        // Fill data rows
	        int rowNum = 1;
	        for (Map<String, String> entry : finalData) {
	            Row row = sheet.createRow(rowNum++);
	            row.createCell(0).setCellValue(entry.getOrDefault("Emp Id", ""));
	            row.createCell(1).setCellValue(entry.getOrDefault("Date", ""));
	            row.createCell(2).setCellValue(entry.getOrDefault("In Time", ""));
	            row.createCell(3).setCellValue(entry.getOrDefault("Out Time", ""));
	            String totalWorkingHours = entry.getOrDefault("Total Working Hours", "00:00");
	            row.createCell(4).setCellValue(totalWorkingHours);
	            
	            // Get Non Working Hours for this employee (using Emp Id and Date)
	            String empIdDate = entry.get("Emp Id") + "-" + entry.get("Date");
	            String nonWorkingHours = nonWorkingHoursMap.getOrDefault(empIdDate, "00:00:00");
	            row.createCell(5).setCellValue(nonWorkingHours);
	            
	            // Set cell style based on total working hours
	         //   CellStyle style = workbook.createCellStyle();
	            if (isLessThan(totalWorkingHours, "09:00")) {
	              //  style.setFillForegroundColor(IndexedColors.RED.getIndex());
	            } else if (isLessThan(totalWorkingHours, "09:30")) {
	              //  style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
	            } else {
	            //    style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
	            }
	           // style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	         //   row.getCell(4).setCellStyle(style);
	        }

	        workbook.write(outputStream);
	        return outputStream.toByteArray();
	    }
	}


	// Helper method to compare time strings in HH:mm format
	private boolean isLessThan(String time1, String time2) {
	    String[] parts1 = time1.split(":");
	    String[] parts2 = time2.split(":");
	    int hours1 = Integer.parseInt(parts1[0]);
	    int minutes1 = Integer.parseInt(parts1[1]);
	    int hours2 = Integer.parseInt(parts2[0]);
	    int minutes2 = Integer.parseInt(parts2[1]);
	    return hours1 < hours2 || (hours1 == hours2 && minutes1 < minutes2);
	}

	
		@GetMapping("/download/{fileName}")
	    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
	        log.info("Attempting to download file: {}", fileName);
	        List<FileModel> fileEntity = fileService.getFile(fileName);

	        if (fileEntity.isEmpty()) {
	            log.warn("File not found: {}", fileName);
	            return ResponseEntity.notFound().build();
	        }

	        String fileData = fileEntity.get(0).getFileData();
	        byte[] decodedData = Base64.getDecoder().decode(fileData);
	        
	        Workbook workbook;
	        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedData)) {
	            if (fileName.endsWith(".xls")) {
	                workbook = new HSSFWorkbook(inputStream); // For .xls files
	            } else if (fileName.endsWith(".xlsx")) {
	                workbook = new XSSFWorkbook(inputStream); // For .xlsx files
	            } else {
	                return ResponseEntity.badRequest().body(null); // Unsupported file type
	            }

	            // Set headers for file download
	            HttpHeaders headers = new HttpHeaders();
	            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
	            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE); // Generic binary stream

	            // Write the workbook to a byte array output stream
	            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	            try {
	                workbook.write(outputStream);
	                workbook.close();
	            } catch (IOException e) {
	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	            }

	            ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

	            return ResponseEntity.ok()
	                    .headers(headers)
	                    .contentLength(resource.contentLength())
	                    .body(resource);
	        } catch (IOException e) {
	            log.error("Error processing file: {}", fileName, e);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    }

}
