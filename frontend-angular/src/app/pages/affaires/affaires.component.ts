import { Component, OnInit } from '@angular/core';
import { AffaireService } from '../../services/affaire.service';
import { Affaire } from '../../models/affaire';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-affaires',
  templateUrl: './affaires.component.html',
  styleUrls: ['./affaires.component.css']
})
export class AffairesComponent implements OnInit {

  affaires: Affaire[] = [];
  isEditMode: boolean = false;
selectedAffaireId?: number;

newAffaire: Affaire = {
  titre: '',
  description: '',
  statut: 'OUVERTE'
};

  constructor(private affaireService: AffaireService ,
    public authService: AuthService

  ) {

  }

  ngOnInit(): void {
    this.loadAffaires();
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

saveAffaire(): void {
  if (!this.newAffaire.titre) {
    alert('Veuillez remplir le titre de l’affaire');
    return;
  }

  if (this.isEditMode && this.selectedAffaireId) {
    this.affaireService.updateAffaire(this.selectedAffaireId, this.newAffaire).subscribe({
      next: () => {
        this.loadAffaires();
        this.resetForm();
      },
      error: (err) => {
        console.error('Erreur modification affaire', err);
      }
    });
  } else {
    this.affaireService.createAffaire(this.newAffaire).subscribe({
      next: () => {
        this.loadAffaires();
        this.resetForm();
      },
      error: (err) => {
        console.error('Erreur ajout affaire', err);
      }
    });
  }
}
editAffaire(affaire: Affaire): void {
  this.isEditMode = true;
  this.selectedAffaireId = affaire.id;

  this.newAffaire = {
    numeroAffaire: affaire.numeroAffaire,
    titre: affaire.titre,
    description: affaire.description,
    statut: affaire.statut
  };
}

resetForm(): void {
  this.isEditMode = false;
  this.selectedAffaireId = undefined;

  this.newAffaire = {
    titre: '',
    description: '',
    statut: 'OUVERTE'
  };
}

  deleteAffaire(id?: number): void {
    if (!id) return;

    if (confirm('Voulez-vous vraiment supprimer cette affaire ?')) {
      this.affaireService.deleteAffaire(id).subscribe({
        next: () => {
          this.loadAffaires();
        },
        error: (err) => {
          console.error('Erreur suppression affaire', err);
        }
      });
    }
  }
}
