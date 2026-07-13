import { Component, OnInit } from '@angular/core';
import { CrimeService } from '../../services/crime.service';
import { AffaireService } from '../../services/affaire.service';
import { Crime } from '../../models/crime';
import { Affaire } from '../../models/affaire';
import { AuthService } from 'src/app/services/auth.service';
import { AiService } from '../../services/ai.service';

@Component({
  selector: 'app-crimes',
  templateUrl: './crimes.component.html',
  styleUrls: ['./crimes.component.css']
})
export class CrimesComponent implements OnInit {

  isEditMode: boolean = false;
  selectedCrimeId?: number;

  filterTypeCrime: string = '';
  filterVille: string = '';
  filterStatut: string = '';

  crimes: Crime[] = [];
  affaires: Affaire[] = [];

  selectedAffaireId: number | null = null;

  newCrime: Crime = {
    typeCrime: '',
    description: '',
    dateCrime: '',
    adresse: '',
    ville: '',
    latitude: null,
    longitude: null,
    statut: 'SIGNALE'
  };

  constructor(
    private crimeService: CrimeService,
    private affaireService: AffaireService,
    public authService: AuthService,
    private aiService: AiService

  ) {}

  ngOnInit(): void {
    this.loadCrimes();
    this.loadAffaires();
  }

  loadCrimes(): void {
    this.crimeService.getAllCrimes().subscribe({
      next: (data) => {
        this.crimes = data;
      },
      error: (err) => {
        console.error('Erreur chargement crimes', err);
      }
    });
  }

  loadAffaires(): void {
    this.affaireService.getAllAffaires().subscribe({
      next: (data) => {
        this.affaires = data;
      },
      error: (err) => {
        console.error('Erreur chargement affaires', err);
      }
    });
  }

saveCrime(): void {
  if (!this.newCrime.typeCrime || !this.newCrime.dateCrime || !this.newCrime.ville) {
    alert('Veuillez remplir type crime, date et ville');
    return;
  }

  const crimeToSend: Crime = {
    ...this.newCrime
  };

  if (this.selectedAffaireId) {
    crimeToSend.affaire = {
      id: this.selectedAffaireId,
      titre: '',
      description: '',
      statut: ''
    };
  }

  if (this.isEditMode && this.selectedCrimeId) {
    this.crimeService.updateCrime(this.selectedCrimeId, crimeToSend).subscribe({
      next: () => {
        this.loadCrimes();
        this.resetForm();
      },
      error: (err) => {
        console.error('Erreur modification crime', err);
      }
    });
  } else {
    this.crimeService.createCrime(crimeToSend).subscribe({
      next: () => {
        this.loadCrimes();
        this.resetForm();
      },
      error: (err) => {
        console.error('Erreur ajout crime', err);
      }
    });
  }
}

  deleteCrime(id?: number): void {
    if (!id) return;

    if (confirm('Voulez-vous vraiment supprimer ce crime ?')) {
      this.crimeService.deleteCrime(id).subscribe({
        next: () => {
          this.loadCrimes();
        },
        error: (err) => {
          console.error('Erreur suppression crime', err);
        }
      });
    }
  }
  applyFilters(): void {
  this.crimeService.searchCrimes(
    this.filterTypeCrime,
    this.filterVille,
    this.filterStatut
  ).subscribe({
    next: (data) => {
      this.crimes = data;
    },
    error: (err) => {
      console.error('Erreur filtre crimes', err);
    }
  });
}

resetFilters(): void {
  this.filterTypeCrime = '';
  this.filterVille = '';
  this.filterStatut = '';
  this.loadCrimes();
}
editCrime(crime: Crime): void {
  this.isEditMode = true;
  this.selectedCrimeId = crime.id;

  this.newCrime = {
    typeCrime: crime.typeCrime,
    description: crime.description,
    dateCrime: crime.dateCrime,
    adresse: crime.adresse,
    ville: crime.ville,
    latitude: crime.latitude,
    longitude: crime.longitude,
    statut: crime.statut
  };

  this.selectedAffaireId = crime.affaire?.id || null;
}

resetForm(): void {
  this.isEditMode = false;
  this.selectedCrimeId = undefined;
  this.selectedAffaireId = null;

  this.newCrime = {
    typeCrime: '',
    description: '',
    dateCrime: '',
    adresse: '',
    ville: '',
    latitude: null,
    longitude: null,
    statut: 'SIGNALE'
  };
}
classifyCrimeWithAi(): void {
  if (!this.newCrime.description) {
    alert('Veuillez écrire une description d’abord');
    return;
  }

  this.aiService.classifyCrime(this.newCrime.description).subscribe({
    next: (response) => {
      this.newCrime.typeCrime = response.typeCrime;

      alert(
        'Type proposé par IA: ' +
        response.typeCrime +
        ' | Confiance: ' +
        Math.round(response.confidence * 100) +
        '%'
      );
    },
    error: (err) => {
      console.error('Erreur classification IA', err);
      alert('Erreur lors de la classification IA');
    }
  });
}


}
