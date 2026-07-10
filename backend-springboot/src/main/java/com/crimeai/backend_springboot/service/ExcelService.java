package com.crimeai.backend_springboot.service;


import com.crimeai.backend_springboot.entity.Crime;
import com.crimeai.backend_springboot.repository.AffaireRepository;
import com.crimeai.backend_springboot.repository.CrimeRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

@Service
public class ExcelService {

    private final CrimeRepository crimeRepository;
    private final AffaireRepository affaireRepository;
    private final DashboardService dashboardService;

    public ExcelService(
            CrimeRepository crimeRepository,
            AffaireRepository affaireRepository,
            DashboardService dashboardService
    ) {
        this.crimeRepository = crimeRepository;
        this.affaireRepository = affaireRepository;
        this.dashboardService = dashboardService;
    }

    public byte[] generateCrimesExcel() {
        try {
            Workbook workbook = new XSSFWorkbook();

            createCrimesSheet(workbook);
            createStatsSheet(workbook);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            workbook.close();

            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du fichier Excel", e);
        }
    }

    private void createCrimesSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet("Crimes");

        Row header = sheet.createRow(0);

        String[] columns = {
                "ID",
                "Type",
                "Description",
                "Date",
                "Ville",
                "Adresse",
                "Latitude",
                "Longitude",
                "Statut",
                "Affaire"
        };

        CellStyle headerStyle = createHeaderStyle(workbook);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }

        List<Crime> crimes = crimeRepository.findAll();

        int rowIndex = 1;

        for (Crime crime : crimes) {
            Row row = sheet.createRow(rowIndex++);

            row.createCell(0).setCellValue(crime.getId() != null ? crime.getId() : 0);
            row.createCell(1).setCellValue(safe(crime.getTypeCrime()));
            row.createCell(2).setCellValue(safe(crime.getDescription()));
            row.createCell(3).setCellValue(crime.getDateCrime() != null ? crime.getDateCrime().toString() : "");
            row.createCell(4).setCellValue(safe(crime.getVille()));
            row.createCell(5).setCellValue(safe(crime.getAdresse()));
            row.createCell(6).setCellValue(crime.getLatitude() != null ? crime.getLatitude() : 0);
            row.createCell(7).setCellValue(crime.getLongitude() != null ? crime.getLongitude() : 0);
            row.createCell(8).setCellValue(safe(crime.getStatut()));

            if (crime.getAffaire() != null) {
                row.createCell(9).setCellValue(safe(crime.getAffaire().getNumeroAffaire()));
            } else {
                row.createCell(9).setCellValue("Non liée");
            }
        }

        autoSizeColumns(sheet, columns.length);
    }

    private void createStatsSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet("Statistiques");

        CellStyle headerStyle = createHeaderStyle(workbook);

        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Statistiques générales");
        titleCell.setCellStyle(headerStyle);

        Row row1 = sheet.createRow(2);
        row1.createCell(0).setCellValue("Total affaires");
        row1.createCell(1).setCellValue(affaireRepository.count());

        Row row2 = sheet.createRow(3);
        row2.createCell(0).setCellValue("Total crimes");
        row2.createCell(1).setCellValue(crimeRepository.count());

        Map<String, Object> stats = dashboardService.getDashboardStats();

        int rowIndex = 6;

        rowIndex = addMapSection(sheet, rowIndex, "Crimes par type", (Map<String, Long>) stats.get("crimesParType"), headerStyle);
        rowIndex = addMapSection(sheet, rowIndex + 2, "Crimes par ville", (Map<String, Long>) stats.get("crimesParVille"), headerStyle);
        rowIndex = addMapSection(sheet, rowIndex + 2, "Crimes par statut", (Map<String, Long>) stats.get("crimesParStatut"), headerStyle);
        addMapSection(sheet, rowIndex + 2, "Affaires par statut", (Map<String, Long>) stats.get("affairesParStatut"), headerStyle);

        autoSizeColumns(sheet, 3);
    }

    private int addMapSection(
            Sheet sheet,
            int rowIndex,
            String title,
            Map<String, Long> data,
            CellStyle headerStyle
    ) {
        Row titleRow = sheet.createRow(rowIndex++);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(title);
        titleCell.setCellStyle(headerStyle);

        Row header = sheet.createRow(rowIndex++);
        header.createCell(0).setCellValue("Nom");
        header.createCell(1).setCellValue("Nombre");

        for (Map.Entry<String, Long> entry : data.entrySet()) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(entry.getKey());
            row.createCell(1).setCellValue(entry.getValue());
        }

        return rowIndex;
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        Font font = workbook.createFont();
        font.setBold(true);

        CellStyle style = workbook.createCellStyle();
        style.setFont(font);

        return style;
    }

    private void autoSizeColumns(Sheet sheet, int numberOfColumns) {
        for (int i = 0; i < numberOfColumns; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}