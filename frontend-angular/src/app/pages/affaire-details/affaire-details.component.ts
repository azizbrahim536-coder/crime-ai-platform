import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Affaire } from '../../models/affaire';
import { Crime } from '../../models/crime';
import { PersonneImpliquee } from '../../models/personne-impliquee';

import { AffaireService } from '../../services/affaire.service';
import { CrimeService } from '../../services/crime.service';
import { PersonneImpliqueeService } from '../../services/personne-impliquee.service';
import { ReportService } from '../../services/report.service';

@Component({
  selector: 'app-affaire-details',
  templateUrl: './affaire-details.component.html',
  styleUrls: ['./affaire-details.component.css']
})
export class AffaireDetailsComponent implements OnInit {

  affaire?: Affaire;
  crimes: Crime[] = [];
  personnes: PersonneImpliquee[] = [];

  suspects: PersonneImpliquee[] = [];
  victimes: PersonneImpliquee[] = [];
  temoins: PersonneImpliquee[] = [];

  affaireId!: number;

  constructor(
    private route: ActivatedRoute,
    private affaireService: AffaireService,
    private crimeService: CrimeService,
    private reportService: ReportService,
    private personneService: PersonneImpliqueeService
  ) {}

  ngOnInit(): void {
    this.affaireId = Number(this.route.snapshot.paramMap.get('id'));

    this.loadAffaire();
    this.loadCrimes();
    this.loadPersonnes();
  }

  loadAffaire(): void {
    this.affaireService.getAffaireById(this.affaireId).subscribe({
      next: (data) => {
        this.affaire = data;
      },
      error: (err) => {
        console.error('Erreur chargement affaire', err);
      }
    });
  }

  loadCrimes(): void {
    this.crimeService.getCrimesByAffaire(this.affaireId).subscribe({
      next: (data) => {
        this.crimes = data;
      },
      error: (err) => {
        console.error('Erreur chargement crimes affaire', err);
      }
    });
  }

  loadPersonnes(): void {
    this.personneService.getPersonnesByAffaire(this.affaireId).subscribe({
      next: (data) => {
        this.personnes = data;

        this.suspects = data.filter(p => p.typePersonne === 'SUSPECT');
        this.victimes = data.filter(p => p.typePersonne === 'VICTIME');
        this.temoins = data.filter(p => p.typePersonne === 'TEMOIN');
      },
      error: (err) => {
        console.error('Erreur chargement personnes affaire', err);
      }
    });
  }

  downloadPdf(): void {
  if (!this.affaireId) return;

  this.reportService.downloadAffairePdf(this.affaireId).subscribe({
    next: (blob) => {
      const fileURL = window.URL.createObjectURL(blob);
      const link = document.createElement('a');

      link.href = fileURL;
      link.download = `rapport-affaire-${this.affaireId}.pdf`;
      link.click();

      window.URL.revokeObjectURL(fileURL);
    },
    error: (err) => {
      console.error('Erreur téléchargement PDF', err);
      alert('Erreur lors du téléchargement du PDF');
    }
  });
}
}
