package com.crimeai.backend_springboot.controller;


import com.crimeai.backend_springboot.service.ExcelService;
import com.crimeai.backend_springboot.service.ReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "http://localhost:4200")
public class ReportController {

    private final ReportService reportService;
    private final ExcelService excelService;

    public ReportController(ReportService reportService, ExcelService excelService) {
        this.reportService = reportService;
        this.excelService = excelService;
    }

    @GetMapping("/affaires/{id}/pdf")
    public ResponseEntity<byte[]> downloadAffairePdf(@PathVariable Long id) {
        byte[] pdf = reportService.generateAffairePdf(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=rapport-affaire-" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/crimes/excel")
    public ResponseEntity<byte[]> downloadCrimesExcel() {
        byte[] excel = excelService.generateCrimesExcel();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=crimes-statistiques.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excel);
    }
}