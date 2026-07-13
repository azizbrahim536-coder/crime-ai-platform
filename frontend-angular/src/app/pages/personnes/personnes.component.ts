import { Component, OnInit } from '@angular/core';
import { PersonneImpliquee } from '../../models/personne-impliquee';
import { Affaire } from '../../models/affaire';
import { PersonneImpliqueeService } from '../../services/personne-impliquee.service';
import { AffaireService } from '../../services/affaire.service';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-personnes',
  templateUrl: './personnes.component.html',
  styleUrls: ['./personnes.component.css']
})
export class PersonnesComponent implements OnInit {

  personnes: PersonneImpliquee[] = [];
  affaires: Affaire[] = [];

  selectedAffaireId: number | null = null;
  isEditMode: boolean = false;
  selectedPersonneId?: number;

  newPersonne: PersonneImpliquee = {
    nom: '',
    prenom: '',
    dateNaissance: '',
    genre: '',
    telephone: '',
    adresse: '',
    typePersonne: 'SUSPECT',
    notes: ''
  };

  constructor(
    private personneService: PersonneImpliqueeService,
    private affaireService: AffaireService,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadPersonnes();
    this.loadAffaires();
  }

  loadPersonnes(): void {
    this.personneService.getAllPersonnes().subscribe({
      next: (data) => {
        this.personnes = data;
      },
      error: (err) => {
        console.error('Erreur chargement personnes', err);
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

  savePersonne(): void {
    if (!this.newPersonne.nom || !this.newPersonne.prenom || !this.newPersonne.typePersonne) {
      alert('Veuillez remplir nom, prénom et type');
      return;
    }

    const personneToSend: PersonneImpliquee = {
      ...this.newPersonne
    };

    if (this.selectedAffaireId) {
      personneToSend.affaire = {
        id: this.selectedAffaireId,
        titre: '',
        description: '',
        statut: ''
      };
    }

    if (this.isEditMode && this.selectedPersonneId) {
      this.personneService.updatePersonne(this.selectedPersonneId, personneToSend).subscribe({
        next: () => {
          this.loadPersonnes();
          this.resetForm();
        },
        error: (err) => {
          console.error('Erreur modification personne', err);
        }
      });
    } else {
      this.personneService.createPersonne(personneToSend).subscribe({
        next: () => {
          this.loadPersonnes();
          this.resetForm();
        },
        error: (err) => {
          console.error('Erreur ajout personne', err);
        }
      });
    }
  }

  editPersonne(personne: PersonneImpliquee): void {
    this.isEditMode = true;
    this.selectedPersonneId = personne.id;

    this.newPersonne = {
      nom: personne.nom,
      prenom: personne.prenom,
      dateNaissance: personne.dateNaissance,
      genre: personne.genre,
      telephone: personne.telephone,
      adresse: personne.adresse,
      typePersonne: personne.typePersonne,
      notes: personne.notes
    };

    this.selectedAffaireId = personne.affaire?.id || null;
  }

  deletePersonne(id?: number): void {
    if (!id) return;

    if (confirm('Voulez-vous vraiment supprimer cette personne ?')) {
      this.personneService.deletePersonne(id).subscribe({
        next: () => {
          this.loadPersonnes();
        },
        error: (err) => {
          console.error('Erreur suppression personne', err);
        }
      });
    }
  }

  resetForm(): void {
    this.isEditMode = false;
    this.selectedPersonneId = undefined;
    this.selectedAffaireId = null;

    this.newPersonne = {
      nom: '',
      prenom: '',
      dateNaissance: '',
      genre: '',
      telephone: '',
      adresse: '',
      typePersonne: 'SUSPECT',
      notes: ''
    };
  }
}
