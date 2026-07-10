package com.crimeai.backend_springboot.service;

import com.crimeai.backend_springboot.entity.Affaire;
import com.crimeai.backend_springboot.entity.Crime;
import com.crimeai.backend_springboot.entity.PersonneImpliquee;
import com.crimeai.backend_springboot.repository.AffaireRepository;
import com.crimeai.backend_springboot.repository.CrimeRepository;
import com.crimeai.backend_springboot.repository.PersonneImpliqueeRepository;
import org.openpdf.text.Document;
import org.openpdf.text.Font;
import org.openpdf.text.FontFactory;
import org.openpdf.text.PageSize;
import org.openpdf.text.Paragraph;
import org.openpdf.text.Phrase;
import org.openpdf.text.pdf.PdfPCell;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReportService {

    private final AffaireRepository affaireRepository;
    private final CrimeRepository crimeRepository;
    private final PersonneImpliqueeRepository personneRepository;

    public ReportService(
            AffaireRepository affaireRepository,
            CrimeRepository crimeRepository,
            PersonneImpliqueeRepository personneRepository
    ) {
        this.affaireRepository = affaireRepository;
        this.crimeRepository = crimeRepository;
        this.personneRepository = personneRepository;
    }

    public byte[] generateAffairePdf(Long affaireId) {
        try {
            Affaire affaire = affaireRepository.findById(affaireId)
                    .orElseThrow(() -> new RuntimeException("Affaire introuvable"));

            List<Crime> crimes = crimeRepository.findByAffaireId(affaireId);
            List<PersonneImpliquee> personnes = personneRepository.findByAffaireId(affaireId);

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);

            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

            Paragraph title = new Paragraph("Rapport de l'affaire", titleFont);
            title.setSpacingAfter(20);
            document.add(title);

            document.add(new Paragraph("Numero affaire : " + safe(affaire.getNumeroAffaire()), normalFont));
            document.add(new Paragraph("Titre : " + safe(affaire.getTitre()), normalFont));
            document.add(new Paragraph("Statut : " + safe(affaire.getStatut()), normalFont));
            document.add(new Paragraph("Date creation : " + safe(String.valueOf(affaire.getDateCreation())), normalFont));
            document.add(new Paragraph("Description : " + safe(affaire.getDescription()), normalFont));

            addSpace(document);

            document.add(new Paragraph("Crimes lies a cette affaire", sectionFont));
            document.add(createCrimesTable(crimes));

            addSpace(document);

            document.add(new Paragraph("Personnes impliquees", sectionFont));
            document.add(createPersonnesTable(personnes));

            document.close();

            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la generation du PDF", e);
        }
    }

    private PdfPTable createCrimesTable(List<Crime> crimes) {
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);

        addHeader(table, "Type");
        addHeader(table, "Ville");
        addHeader(table, "Adresse");
        addHeader(table, "Statut");
        addHeader(table, "Date");

        for (Crime crime : crimes) {
            table.addCell(safe(crime.getTypeCrime()));
            table.addCell(safe(crime.getVille()));
            table.addCell(safe(crime.getAdresse()));
            table.addCell(safe(crime.getStatut()));
            table.addCell(safe(String.valueOf(crime.getDateCrime())));
        }

        return table;
    }

    private PdfPTable createPersonnesTable(List<PersonneImpliquee> personnes) {
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);

        addHeader(table, "Type");
        addHeader(table, "Nom");
        addHeader(table, "Prenom");
        addHeader(table, "Genre");
        addHeader(table, "Telephone");

        for (PersonneImpliquee personne : personnes) {
            table.addCell(safe(personne.getTypePersonne()));
            table.addCell(safe(personne.getNom()));
            table.addCell(safe(personne.getPrenom()));
            table.addCell(safe(personne.getGenre()));
            table.addCell(safe(personne.getTelephone()));
        }

        return table;
    }

    private void addHeader(PdfPTable table, String text) {
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        table.addCell(cell);
    }

    private void addSpace(Document document) throws Exception {
        document.add(new Paragraph(" "));
    }

    private String safe(String value) {
        return value == null || value.equals("null") ? "" : value;
    }
}